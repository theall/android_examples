/**
 * Copyright (C) Bilge Theall, wazcd_1608@qq.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.wx.multihero.base;

import android.provider.ContactsContract;

import java.io.DataInput;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LittleEndianDataInputStream  extends FilterInputStream implements DataInput {
    /**
     * Constructs a new {@code FilterInputStream} with the specified input
     * stream as source.
     *
     * <p><strong>Warning:</strong> passing a null source creates an invalid
     * {@code FilterInputStream}, that fails on every method that is not
     * overridden. Subclasses should check for null in their constructors.
     *
     * @param in the input stream to filter reads on.
     */
    protected LittleEndianDataInputStream(InputStream in) {
        super(in);
    }

    public boolean readBoolean() throws IOException {
        return false;
    }

    public final byte readByte() throws IOException {
        int temp = in.read();
        if (temp < 0) {
            throw new EOFException();
        }
        return (byte) temp;
    }

    public char readChar() throws IOException {
        return (char)readByte();
    }

    public double readDouble() throws IOException {
        return 0;
    }

    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public void readFully(byte[] dst) throws IOException {

    }

    public void readFully(byte[] dst, int offset, int byteCount) throws IOException {

    }

    public int readInt() throws IOException {
        byte a1 = readAndCheckByte();
        byte a2 = readAndCheckByte();
        byte a3 = readAndCheckByte();
        byte a4 = readAndCheckByte();
        return a4 << 24 | (a3 & 0xFF) << 16 | (a2 & 0xFF) << 8 | (a1 & 0xFF);
    }

    public String readLine() throws IOException {
        return null;
    }

    public long readLong() throws IOException {
        return 0;
    }

    public short readShort() throws IOException {
        return 0;
    }

    public int readUnsignedByte() throws IOException {
        return 0;
    }

    public int readUnsignedShort() throws IOException {
        return 0;
    }

    public String readUTF() throws IOException {
        return null;
    }

    public int skipBytes(int count) throws IOException {
        return (int) in.skip(count);
    }

    public final String readString() throws IOException {
        String result = "";
        char c = '\0';
        do {
            c = readChar();
            result += c;
        } while (c!=0);
        return result;
    }


    /**
     * Reads a byte from the input stream checking that the end of file (EOF) has not been
     * encountered.
     *
     * @return byte read from input
     * @throws IOException if an error is encountered while reading
     * @throws EOFException if the end of file (EOF) is encountered.
     */
    private byte readAndCheckByte() throws IOException, EOFException {
        int b1 = in.read();

        if (-1 == b1) {
            throw new EOFException();
        }

        return (byte) b1;
    }
}