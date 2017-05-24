package com.fise.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.framework.config.ConfigProperties;
import com.fise.model.result.OssUploadSignResult;
import com.qq.jutil.j4log.Logger;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-30
 * @desc 阿里云oss相关工具
 */

public class OSSUtil {
	// bucket地址
    private static String bucket = ConfigProperties.getValue("aliyun_oss_fitness_bucket");
    
    // access key id
    private static String accessKeyId = ConfigProperties.getValue("aliyun_oss_access_key_id");
    
    // access key secret
    private static String accessSecret = ConfigProperties.getValue("aliyun_oss_access_key_secret");
    
    // oss client
    private static OSSClient ossClient = new OSSClient(bucket, accessKeyId, accessSecret);
    
    // 
    public static Response genSign() {
    	Logger logger = Logger.getLogger("oss_gen_sign");
    	
        Response resp = new Response();
        
        // 设置超时时间10min
        Date expiration = new Date(System.currentTimeMillis() + Constants.ALIYUN_OSS_UPLOAD_URL_EXPIRE_MILLISECONDS);
        
        // 上传文件小于10M
        PolicyConditions postPolicy = new PolicyConditions();
        // postPolicy.addConditionItem("Content-Length-Range", 0, 10240000);
        postPolicy.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE,0, 10240000);
        // postPolicy.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, "dev");
        String policyString = ossClient.generatePostPolicy(expiration, postPolicy);
        
        byte[] binaryData = null;
        try {
            binaryData = policyString.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            resp.failure(ErrorCode.ERROR_SYSTEM);
            return resp;
        }
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String sign = ossClient.calculatePostSignature(policyString);
        
        OssUploadSignResult result = new OssUploadSignResult();
        result.setUploadUrl(bucket);
        result.setExpireTime(expiration.getTime() / 1000);
        result.setAccessKeyId(accessKeyId);
        result.setPrefix(UUID.randomUUID().toString().replace("-", ""));
        result.setSign(sign);
        result.setPolicy(encodedPolicy);
        resp.success(result);
        
        logger.debug("result:" + result.toString());
        return resp;
    }
    
}
