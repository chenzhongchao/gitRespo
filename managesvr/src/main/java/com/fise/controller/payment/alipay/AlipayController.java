package com.fise.controller.payment.alipay;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.framework.annotation.IgnoreAuth;
import com.fise.model.entity.Member;
import com.fise.model.entity.MemberAccount;
import com.fise.model.entity.MemberAccountTransaction;
import com.fise.model.param.AlipayParam;
import com.fise.service.account.IAccountService;
import com.fise.service.account.IAccountTransactionService;
import com.fise.service.member.IMemberService;
import com.fise.utils.CommonUtil;
import com.fise.utils.Constants;
import com.fise.utils.payment.alipay.util.AlipayUtil;
import com.qq.jutil.j4log.Logger;

@RestController
@RequestMapping("/member")
public class AlipayController {
	@Resource
	private IAccountService accountService;
	
	@Resource
	private IMemberService memberService;
	
	@Resource
	private IAccountTransactionService accountTransactionService;
	
	private final String TRADE_SUCCESS = "TRADE_SUCCESS";		// 支付宝回调状态：交易成功

	private final String TRADE_FINISHED = "TRADE_FINISHED";		// 支付宝回调状态： 支付成功
	
	
	@RequestMapping(value = "/alipay/payinfo", method = RequestMethod.POST)
	public Response genPayInfo(@RequestBody @Valid AlipayParam param, HttpServletRequest request) {
		Response resp = new Response();
		Logger logger = Logger.getLogger("alipay_payinfo");
		
		logger.debug("AlipayParam = " + param.toString());
		logger.debug("HttpServletRequest = " + request.toString());
		
		Integer memberId = param.getMemberId();
		Long chargeAmount = param.getChargeAmount();
		
		Member member = memberService.getMemberById(memberId);
		
		// 生成交易流水  prebalance和postbalance放在callback里面写
		String transNo = CommonUtil.getTransactionNo(member);
		MemberAccountTransaction memberAccountTransaction = new MemberAccountTransaction(); 
		memberAccountTransaction.setTransNo(transNo);
		memberAccountTransaction.setMemberId(member.getMemberId());
		memberAccountTransaction.setOperateType(Constants.ACCOUNT_TRANSACTION_OPERATE_TYPE_ACCOUNT_CHARGE);
		memberAccountTransaction.setPayMethod(Constants.PAY_METHOD_ALIPAY_MOBILE_SECURITY_PAY);
		memberAccountTransaction.setAmount(chargeAmount);
		memberAccountTransaction.setState(Constants.ACCOUNT_TRANSACTION_STATE_PAY_NOT);
		accountTransactionService.insertMemberAccountTransaction(memberAccountTransaction);
		
		logger.debug("MemberAccountTransaction = " + memberAccountTransaction.toString());
		
		String chargeAmountString = BigDecimal.valueOf(Long.valueOf(chargeAmount)).divide(new BigDecimal(100)).toString();  

		// 生成payInfo
		String payInfo = null;
		try {
			payInfo = AlipayUtil.genPayInfo(transNo, chargeAmountString);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			resp.failure(ErrorCode.ERROR_SYSTEM);
			return resp;
		}
		logger.debug("payInfo = " + payInfo);
		resp.success(payInfo);
		
		return resp;
	}
	
	@IgnoreAuth
	@RequestMapping(value = "/alipay/callback", method = RequestMethod.POST)
	public String callback(HttpServletRequest request,
			@RequestParam("out_trade_no") String outTradeNo,
			@RequestParam("trade_no") String tradeNo,
			@RequestParam("trade_status") String tradeStatus,
			@RequestParam("total_fee") Double totalFee) 
	{
		Logger logger = Logger.getLogger("alipay_callback");
		logger.debug("request = " + request + " out_trade_no="+outTradeNo + " tradeNo="+tradeNo + " trade_status="+tradeStatus + " totalFee=" + totalFee);
		if (!AlipayUtil.verify(request)) {
		    // logger.info("签名校验不一致，回调失败");
			return "IllegerCallback";
		}
		
		try {
			// 签名回调校验成功，处理订单逻辑
			if (TRADE_SUCCESS.equals(tradeStatus) || TRADE_FINISHED.equals(tradeStatus)) {
				// logger.info("签名校验成功，订单支付成功，开始处理订单逻辑");
				MemberAccountTransaction memberAccountTransaction = accountTransactionService.getMemberAccountTransactionByTransNo(outTradeNo);
				if (memberAccountTransaction == null) {
					// TODO  记录错误日志
					return "fail";
				}
				
				// 避免重复回调
				if (memberAccountTransaction.getState() == Constants.ACCOUNT_TRANSACTION_STATE_PAY_YES) {
					return "success";
				}
				
				Double totalFeeinFen = totalFee * 100;
				Long chargeAmount = totalFeeinFen.longValue();
				
				if (chargeAmount.longValue() != memberAccountTransaction.getAmount().longValue()) {
					// TODO 记录日志， why ！！！
				}
				
				// 获取用户账户信息
				MemberAccount memberAccount = accountService.getMemberAccountByMemberId(memberAccountTransaction.getMemberId());
				Long availableBalance = memberAccount.getAvailableBalance();
				Long preAvailableBalance = availableBalance;
				Long postAvailableBalance = preAvailableBalance + memberAccountTransaction.getAmount();
				availableBalance = postAvailableBalance;
				memberAccount.setAvailableBalance(availableBalance);
				accountService.updateMemberAccountBalance(memberAccount);		// 更新账户余额
				
				// 更新交易流水状态
				MemberAccountTransaction memberAccountTransactionForUpdate = new MemberAccountTransaction();
				memberAccountTransactionForUpdate.setTransId(memberAccountTransaction.getTransId());
				memberAccountTransactionForUpdate.setPreBalance(preAvailableBalance);
				memberAccountTransactionForUpdate.setPostBalance(postAvailableBalance);
				memberAccountTransactionForUpdate.setState(Constants.ACCOUNT_TRANSACTION_STATE_PAY_YES);
				accountTransactionService.updateMemberAccountTransaction(memberAccountTransactionForUpdate);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Exception = " + e.getMessage());
		} finally { 
		    // 
		}
		
		return "success";
	}
}
