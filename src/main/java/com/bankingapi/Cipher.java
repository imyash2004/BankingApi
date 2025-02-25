package com.bankingapi;

public class Cipher {
    private static final int BLOCK_SIZE = 8;  // Block size in bytes (64 bits)
    private static final int KEY_SIZE = 16;   // Key size in bytes (128 bits)
    private static final int NUM_ROUNDS = 10; // Number of rounds for encryption

    private static final byte[] S_BOX = {
            (byte) 0x63, (byte) 0x7C, (byte) 0x77, (byte) 0x7B, (byte) 0xF2, (byte) 0x6B, (byte) 0x6F, (byte) 0xC5,
            (byte) 0x30, (byte) 0x01, (byte) 0x67, (byte) 0x2B, (byte) 0xFE, (byte) 0xD7, (byte) 0xAB, (byte) 0x76
            // S-box shortened for simplicity
    };

    private static final byte[] REVERSE_S_BOX = new byte[256];

    static {
        for (int i = 0; i < S_BOX.length; i++) {
            REVERSE_S_BOX[S_BOX[i] & 0xFF] = (byte) i;
        }
    }

    private byte[][] roundKeys;

    public Cipher(byte[] key) {
        if (key.length != KEY_SIZE) {
            throw new IllegalArgumentException("Invalid key size!");
        }
        roundKeys = generateRoundKeys(key);
    }

    private byte[][] generateRoundKeys(byte[] key) {
        byte[][] keys = new byte[NUM_ROUNDS][BLOCK_SIZE];
        byte[] currentKey = new byte[KEY_SIZE];

        for (int i = 0; i < KEY_SIZE; i++) {
            currentKey[i] = key[i];
        }

        for (int round = 0; round < NUM_ROUNDS; round++) {
            for (int i = 0; i < BLOCK_SIZE; i++) {
                keys[round][i] = (byte) ((currentKey[i] + round) & 0xFF);
            }

            // Simple key schedule emulation
            for (int i = 0; i < KEY_SIZE; i++) {
                currentKey[i] = (byte) (currentKey[i] ^ (i + round));
            }
        }

        return keys;
    }

    public byte[] encrypt(byte[] plaintext) {
        byte[] block = new byte[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            block[i] = plaintext[i];
        }

        for (int round = 0; round < NUM_ROUNDS; round++) {
            for (int i = 0; i < BLOCK_SIZE; i++) {
                block[i] ^= roundKeys[round][i];
            }

            for (int i = 0; i < BLOCK_SIZE; i++) {
                block[i] = substitute(block[i]);
            }

            block = permute(block);
        }

        return block;
    }

    public byte[] decrypt(byte[] ciphertext) {
        byte[] block = new byte[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            block[i] = ciphertext[i];
        }

        for (int round = NUM_ROUNDS - 1; round >= 0; round--) {
            block = inversePermute(block);

            for (int i = 0; i < BLOCK_SIZE; i++) {
                block[i] = reverseSubstitute(block[i]);
            }

            for (int i = 0; i < BLOCK_SIZE; i++) {
                block[i] ^= roundKeys[round][i];
            }
        }

        return block;
    }

    private byte substitute(byte value) {
        return S_BOX[value & 0xFF];
    }

    private byte reverseSubstitute(byte value) {
        return REVERSE_S_BOX[value & 0xFF];
    }

    private byte[] permute(byte[] block) {
        byte[] permuted = new byte[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            permuted[i] = (byte) ((block[i] << 1) | (block[i] >>> 7));
        }
        return permuted;
    }

    private byte[] inversePermute(byte[] block) {
        byte[] inverse = new byte[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            inverse[i] = (byte) ((block[i] >>> 1) | (block[i] << 7));
        }
        return inverse;
    }
}