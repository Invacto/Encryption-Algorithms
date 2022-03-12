public class Main {

    public static void main(String[] args) {

        Encryptor encryptor = new Encryptor(2, 3);

        Key key = new Key(4, 5, 20);

        String result = encryptor.superEncryptMessage("Hello this is a secret message", key);

        String decrypted = encryptor.superDecryptMessage(result, key);

        System.out.println(result);
        System.out.println(decrypted);

    }
}
