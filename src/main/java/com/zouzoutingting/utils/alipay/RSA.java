package com.zouzoutingting.utils.alipay;

import com.zouzoutingting.common.Global;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA{
    
    public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
    
    /**
    * RSA签名
    * @param content 待签名数据
    * @param privateKey 商户私钥
    * @param input_charset 编码格式
    * @return 签名值
    */
    public static String sign(String content, String privateKey, String input_charset)
    {
        try 
        {
            PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
            KeyFactory keyf                 = KeyFactory.getInstance("RSA");
            PrivateKey priKey               = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(input_charset) );

            byte[] signed = signature.sign();
            
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
    * RSA验签名检查
    * @param content 待签名数据
    * @param sign 签名值
    * @param ali_public_key 支付宝公钥
    * @param input_charset 编码格式
    * @return 布尔值
    */
    public static boolean verify(String content, String sign, String ali_public_key, String input_charset)
    {
        try 
        {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(ali_public_key);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

        
            java.security.Signature signature = java.security.Signature
            .getInstance(SIGN_ALGORITHMS);
        
            signature.initVerify(pubKey);
            signature.update( content.getBytes(input_charset) );
        
            boolean bverify = signature.verify( Base64.decode(sign) );
            return bverify;
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
    * 解密
    * @param content 密文
    * @param private_key 商户私钥
    * @param input_charset 编码格式
    * @return 解密后的字符串
    */
    public static String decrypt(String content, String private_key, String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }

    
    /**
    * 得到私钥
    * @param key 密钥字符串（经过base64编码）
    * @throws Exception
    */
    public static PrivateKey getPrivateKey(String key) throws Exception {

        byte[] keyBytes;
        
        keyBytes = Base64.decode(key);
        
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        
        return privateKey;
    }

    public static void main(String[] args) throws Exception{
        String data = "body=走走听听语音包&buyer_email=13146470322&buyer_id=2088302556226605&discount=0.00&gmt_create=2016-08-30 00:12:03&gmt_payment=2016-08-30 00:12:03&is_total_fee_adjust=N&notify_id=13b851826b6f75f03c413954a9063adkmq&notify_time=2016-08-30 00:12:03&notify_type=trade_status_sync&out_trade_no=200&payment_type=1&price=0.03&quantity=1&seller_email=zouzoutingting@aliyun.com&seller_id=2088321043087101&subject=走走听听语音包&total_fee=0.03&trade_no=2016083021001004600283902945&trade_status=TRADE_SUCCESS&use_coupon=N";
        String sdata = "Ss070/aFj7RdNwkxDD71oyR9WNCGX613DrrHeFZDhdSuVPvCr9lm1ZwKIumsmMCUbOWuxUL8kfNeaR3fN/1sfvecFTUb" +
                "/JAlFItSODhoGQ9BwE2XSAUj5HAJ7LK+xwl2mEfVnMOZ89eA5orOXkc+EcWXXlAcLN9Z5CqxeToxp0g=";
        String sign = RSA.sign(data, Global.ALI_PAY_PARTNER_PRIVATE_KEY, Global.ALI_PAY_INPUT_CHARSET);
        System.out.println(sign);
        System.out.println(RSA.verify(data, sdata, Global.ALI_PAY_PARTNER_PUB_KEY, Global.ALI_PAY_INPUT_CHARSET));
//        System.out.println(RSA.decrypt(sdata, Global.ALI_PAY_PARTNER_PUB_KEY, Global.ALI_PAY_INPUT_CHARSET));
    }
}

