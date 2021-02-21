import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RC4StreamCipher {
        private int[] SArray;
        private int[] keyArray;
        private int KeyLength;

        private final int SArrayLength=256;

        public RC4StreamCipher(byte[] key) {
            if (key.length < 1 || key.length > SArrayLength) {
                throw new RuntimeException("Invalid Key : Key must have length between 1 and 256 bytes");
            }
            KeyLength = key.length;
            KSA(key);
        }
   
        // Key scheduling algorithm
        public void KSA(byte[] key)
        {
            SArray = new int[SArrayLength];
            keyArray = new int[SArrayLength];
            for (int i = 0; i < SArrayLength; i++) {
                SArray[i] = i;
                keyArray[i] = key[i % KeyLength];
            }
            int j = 0;
            for (int i = 0; i < SArrayLength; i++) {
                j = (j + SArray[i] + keyArray[i]) % SArrayLength;
                SArray[i] ^= SArray[j];
                SArray[j] ^= SArray[i];
                SArray[i] ^= SArray[j];
            }
        }
        //Pseudo Random Generation Algorithm
        public byte[] PRGA(byte[] plainData) {
            byte[] ciphertext = new byte[plainData.length];
            int i = 0, j = 0, keyStream, temp;
            for (int k = 0; k < plainData.length; k++) {
                i = (i + 1) % SArrayLength;
                j = (j + SArray[i]) % SArrayLength;
                SArray[i] ^= SArray[j];
                SArray[j] ^= SArray[i];
                SArray[i] ^= SArray[j];
                temp = (SArray[i] + SArray[j]) % SArrayLength;
                keyStream = SArray[temp];
                ciphertext[k] = (byte) (plainData[k] ^ keyStream);
            }
            return ciphertext;
        }

        public byte[] encrypt(byte[] plainData)
        {
            return PRGA(plainData);
        }

        public byte[] decrypt(byte[] cipherText)
        {
            return encrypt(cipherText);
        }




    public static void main(String [] args) throws IOException {

            Scanner sc = new Scanner(System.in);

            System.out.println("Enter Plain Text :");

            String text=sc.nextLine();
            System.out.println("Enter Key :");
            String Ikey=sc.nextLine();
            byte[] key = Ikey.getBytes("ASCII");
            byte[] ptext=text.getBytes("ASCII");

            String Ascii="ASCII encoded plaint text: ";
            for(int i=0; i<ptext.length;i++)
            {
                Ascii += ptext[i]+" ";

            }
            System.out.print("\nPlain Text Entered : " + new String(ptext)+" \n"+Ascii);
            System.out.println("\n");

            //Encryption

            RC4StreamCipher rc4=new RC4StreamCipher(key);
            byte[] ct=rc4.encrypt(ptext);
            System.out.print("Cipher Text or Encrypted Text: ");
            for(int i=0; i<ct.length;i++)
            {
                System.out.print(ct[i]);
            }
            System.out.println("\n");

            //Decryption

            RC4StreamCipher rc4a=new RC4StreamCipher(key);
            byte[] decrypted=rc4a.decrypt(ct);
            String ASCII="ASCII encoded decrypted text: ";
            for(int i=0; i<decrypted.length;i++)
            {
                ASCII+=decrypted[i]+" ";
            }
            System.out.print("Decrypted Text : " + new String(decrypted)+"\n"+ASCII);
            System.out.println();
        }



}
