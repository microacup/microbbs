/*
 * @(#)ShortUUID.java  2016年9月19日	
 *
 * Copyright  2016 HXDD Corporation Copyright (c) All rights reserved.
 */

package me.micro.bbs.util;

import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.util.UUID;


/**
 * UUID
 * 
 * @author: SUNX
 * @version: 2016年9月19日
 */
public class ShortUUID {
    
    /**
     * 去掉了_和-字符
     * 
     * @return 伪base64,全小写
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        String shortID = Base64.encodeBase64URLSafeString(bb.array());
        shortID = shortID.replace("-", "");
        shortID = shortID.replace("_", "");
        return shortID.toLowerCase();
    }
}
