/**
 * 本作品使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权。
 * Copyright (c) 2016 SiCheng.Net
 * This software is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */
package com.sicheng.common.zip;

interface ZipConstants {
    /*
     * Header signatures
     */
    long LOCSIG = 0x04034b50L;    // "PK\003\004"
    long EXTSIG = 0x08074b50L;    // "PK\007\008"
    long CENSIG = 0x02014b50L;    // "PK\001\002"
    long ENDSIG = 0x06054b50L;    // "PK\005\006"

    /*
     * Header sizes in bytes (including signatures)
     */
    int LOCHDR = 30;    // LOC header size
    int EXTHDR = 16;    // EXT header size
    int CENHDR = 46;    // CEN header size
    int ENDHDR = 22;    // END header size

    /*
     * Local file (LOC) header field offsets
     */
    int LOCVER = 4;    // version needed to extract
    int LOCFLG = 6;    // general purpose bit flag
    int LOCHOW = 8;    // compression method
    int LOCTIM = 10;    // modification time
    int LOCCRC = 14;    // uncompressed file crc-32 value
    int LOCSIZ = 18;    // compressed size
    int LOCLEN = 22;    // uncompressed size
    int LOCNAM = 26;    // filename length
    int LOCEXT = 28;    // extra field length

    /*
     * Extra local (EXT) header field offsets
     */
    int EXTCRC = 4;    // uncompressed file crc-32 value
    int EXTSIZ = 8;    // compressed size
    int EXTLEN = 12;    // uncompressed size

    /*
     * Central directory (CEN) header field offsets
     */
    int CENVEM = 4;    // version made by
    int CENVER = 6;    // version needed to extract
    int CENFLG = 8;    // encrypt, decrypt flags
    int CENHOW = 10;    // compression method
    int CENTIM = 12;    // modification time
    int CENCRC = 16;    // uncompressed file crc-32 value
    int CENSIZ = 20;    // compressed size
    int CENLEN = 24;    // uncompressed size
    int CENNAM = 28;    // filename length
    int CENEXT = 30;    // extra field length
    int CENCOM = 32;    // comment length
    int CENDSK = 34;    // disk number start
    int CENATT = 36;    // internal file attributes
    int CENATX = 38;    // external file attributes
    int CENOFF = 42;    // LOC header offset

    /*
     * End of central directory (END) header field offsets
     */
    int ENDSUB = 8;    // number of entries on this disk
    int ENDTOT = 10;    // total number of entries
    int ENDSIZ = 12;    // central directory size in bytes
    int ENDOFF = 16;    // offset of first CEN header
    int ENDCOM = 20;    // zip file comment length
}
