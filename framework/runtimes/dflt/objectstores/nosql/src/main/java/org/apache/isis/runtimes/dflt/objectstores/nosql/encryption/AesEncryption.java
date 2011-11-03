/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.isis.runtimes.dflt.objectstores.nosql.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.runtimes.dflt.objectstores.nosql.DataEncryption;
import org.apache.isis.runtimes.dflt.objectstores.nosql.NoSqlStoreException;

/**
 * NOTE this does not work at the moment
 */
public class AesEncryption implements DataEncryption {

    private static final String AES = "AES";
    private final byte[] specKey;
    
    public AesEncryption() {
        specKey = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
                0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
    }

    public void init(IsisConfiguration configuration) {}
    
    public String getType() {
        return AES;
    }

    public String encrypt(String plainText) {
        try {
            SecretKeySpec key = new SecretKeySpec(specKey, AES);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return new String(cipher.doFinal(plainText.getBytes()));
        } catch (Exception e) {
            throw new NoSqlStoreException(e);
        }
    }

    public String decrypt(String encryptedText) {
        try {
            SecretKeySpec key = new SecretKeySpec(specKey, AES);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(encryptedText.getBytes());
            return new String(decrypted);
        } catch (Exception e) {
            throw new NoSqlStoreException(e);
        }
    }

}