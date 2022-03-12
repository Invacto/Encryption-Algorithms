public class Encryptor
{
    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;
    private String[][][] letter3DBlock;

    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;

    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;

    /** Constructor*/
    public Encryptor(int r, int c)
    {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }

    public String[][] getLetterBlock()
    {
        return letterBlock;
    }

    /** Places a string into letterBlock in row-major order.
     *
     *   @param str  the string to be processed
     *
     *   Postcondition:
     *     if str.length() < numRows * numCols, "A" in each unfilled cell
     *     if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str)
    {

        for (int row = 0; row < letterBlock.length; row++){
            for (int col = 0; col < letterBlock[0].length; col++) {
                letterBlock[row][col] = null;
            }
        }

        int x = 0;
        for (int rows = 0; rows < letterBlock.length; rows++)
        {
            for (int cols = 0; cols < letterBlock[0].length; cols++)
            {
                if (x != str.length()) {
                    letterBlock[rows][cols] = str.substring(x, x + 1);
                    x++;
                }
            }
        }
        for (int rows = 0; rows < letterBlock.length; rows++)
        {
            for (int cols = 0; cols < letterBlock[0].length; cols++)
            {
                if (letterBlock[rows][cols] == null)
                {
                    letterBlock[rows][cols] = "A";
                }
            }
        }
    }

    /** Extracts encrypted string from letterBlock in column-major order.
     *
     *   Precondition: letterBlock has been filled
     *
     *   @return the encrypted string from letterBlock
     */
    public String encryptBlock()
    {
        String result = "";

        for (int col = 0; col < letterBlock[0].length; col++) {
            for (int row = 0; row < letterBlock.length; row++) {
                result += letterBlock[row][col];
            }
        }

        return result;

    }

    /** Encrypts a message.
     *
     *  @param message the string to be encrypted
     *
     *  @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message)
    {
        String result = "";

        int letterBlockLength = letterBlock.length * (letterBlock[0].length);
        int amountOfBlocks = (int) ((message.length() / ((double) letterBlockLength)) + 1);

        for (int i = 0; i < amountOfBlocks; i++) {
            String block = "";

            if (i == 0) {
                block = message.substring(0, i + letterBlockLength);
            } else {
                if (i != amountOfBlocks - 1) {
                    block = message.substring(i * letterBlockLength, ((i + 1) * letterBlockLength));
                } else {
                    block = message.substring(i * letterBlockLength);
                }
            }

            if (block.equals("")) break;

            fillBlock(block);
            result += encryptBlock();
        }

        return result;
    }

    /**  Decrypts an encrypted message. All filler 'A's that may have been
     *   added during encryption will be removed, so this assumes that the
     *   original message (BEFORE it was encrypted) did NOT end in a capital A!
     *
     *   NOTE! When you are decrypting an encrypted message,
     *         be sure that you have initialized your Encryptor object
     *         with the same row/column used to encrypted the message! (i.e.
     *         the “encryption key” that is necessary for successful decryption)
     *         This is outlined in the precondition below.
     *
     *   Precondition: the Encryptor object being used for decryption has been
     *                 initialized with the same number of rows and columns
     *                 as was used for the Encryptor object used for encryption.
     *
     *   @param encryptedMessage  the encrypted message to decrypt
     *
     *   @return  the decrypted, original message (which had been encrypted)
     *
     *   TIP: You are encouraged to create other helper methods as you see fit
     *        (e.g. a method to decrypt each section of the decrypted message,
     *         similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage)
    {
        String result = "";

        int letterBlockLength = letterBlock.length * (letterBlock[0].length);
        int amountOfBlocks = (int) ((encryptedMessage.length() / ((double) letterBlockLength)) + 1);

        for (int i = 0; i < amountOfBlocks; i++) {
            String block = "";

            if (i == 0) {
                block = encryptedMessage.substring(0, i + letterBlockLength);
            } else {
                if (i != amountOfBlocks - 1) {
                    block = encryptedMessage.substring(i * letterBlockLength, ((i + 1) * letterBlockLength));
                } else {
                    block = encryptedMessage.substring(i * letterBlockLength);
                }
            }

            if (block.equals("")) break;

            fillBlockCol(block);

            for (int row = 0; row < letterBlock.length; row++){
                for (int col = 0; col < letterBlock[0].length; col++) {
                    result += letterBlock[row][col];
                }
            }

        }

        for (int i = result.length() - 1; i > -1; i--) {
            if (result.charAt(i) != 'A') {
                result = result.substring(0, i + 1);
                return result;
            }
        }

        return result;
    }

    private void fillBlockCol(String str)
    {
        for (int col = 0; col < letterBlock[0].length; col++){
            for (int row = 0; row < letterBlock.length; row++) {
                letterBlock[row][col] = null;
            }
        }

        int x = 0;
        for (int cols = 0; cols < letterBlock[0].length; cols++) {
            for (int rows = 0; rows < letterBlock.length; rows++)
            {
                if (x != str.length())
                {
                    letterBlock[rows][cols] = str.substring(x, x + 1);
                    x++;
                }
            }
        }
    }

    public String superEncryptMessage(String message, Key key) {

        int x = key.getX();
        int y = key.getY();
        int z = key.getZ();

        String result = "";

        int letterBlockLength = x * y * z;
        int amountOfBlocks = (int) ((message.length() / ((double) letterBlockLength)) + 1);

        for (int i = 0; i < amountOfBlocks; i++) {

            String block = "";

            if (amountOfBlocks == 1) {
                block = message;
            } else if (i == 0) {
                block = message.substring(0, i + letterBlockLength);
            } else {
                if (i != amountOfBlocks - 1) {
                    block = message.substring(i * letterBlockLength, ((i + 1) * letterBlockLength));
                } else {
                    block = message.substring(i * letterBlockLength);
                }
            }

            if (block.equals("")) break;

            fill3DBlock(block, x, y, z);

            for (int yAxis = 0; yAxis < letter3DBlock.length; yAxis++) {

                for (int xAxis = 0; xAxis < letter3DBlock[0].length; xAxis++) {

                    for (int zAxis = 0; zAxis < letter3DBlock[0][0].length; zAxis++) {

                        result += letter3DBlock[yAxis][xAxis][zAxis];

                    }
                }
            }
        }

        return result;
    }

    public String superDecryptMessage(String message, Key key) {

        int x = key.getX();
        int y = key.getY();
        int z = key.getZ();

        String result = "";

        int letterBlockLength = x * y * z;
        int amountOfBlocks = (int) ((message.length() / ((double) letterBlockLength)) + 1);

        for (int i = 0; i < amountOfBlocks; i++) {

            String block = "";

            //make private helper method for this
            if (amountOfBlocks == 1) {
                block = message;
            } else if (i == 0) {
                block = message.substring(0, i + letterBlockLength);
            } else {
                if (i != amountOfBlocks - 1) {
                    block = message.substring(i * letterBlockLength, ((i + 1) * letterBlockLength));
                } else {
                    block = message.substring(i * letterBlockLength);
                }
            }

            if (block.equals("")) break;

            fill3DBlockForDecryption(block, z, x, y);

            for (int xAxis = letter3DBlock[0].length - 1; xAxis > -1; xAxis--) {

                for (int yAxis = letter3DBlock.length - 1; yAxis > -1; yAxis--) {

                    for (int zAxis = letter3DBlock[0][0].length - 1; zAxis > -1; zAxis--) {

                        result = letter3DBlock[yAxis][xAxis][zAxis] + result;

                    }
                }
            }
        }

        String reversedResult = "";

        for (int i = result.length() - letterBlockLength; i > -1; i -= letterBlockLength) {
            reversedResult += result.substring(i, i + letterBlockLength);
        }

        for (int i = reversedResult.length() - 1; i > -1; i--) {
            if (reversedResult.charAt(i) != 'A') {
                reversedResult = reversedResult.substring(0, i + 1);
                return reversedResult;
            }
        }

        return reversedResult;

    }

    private void fill3DBlock(String message, int x, int y, int z) {

        String[] chars = message.split("");

        letter3DBlock = new String[y][x][z];

        int indexCount = 0;

        for (int zAxis = 0; zAxis < letter3DBlock[0][0].length; zAxis++) {

            for (int xAxis = 0; xAxis < letter3DBlock[0].length; xAxis++) {

                for (int yAxis = 0; yAxis < letter3DBlock.length; yAxis++) {

                    if (indexCount < chars.length) {
                        letter3DBlock[yAxis][xAxis][zAxis] = chars[indexCount];
                        indexCount++;
                    } else {
                        letter3DBlock[yAxis][xAxis][zAxis] = "A";
                    }
                }
            }
        }
    }

    private void fill3DBlockForDecryption(String message, int x, int y, int z) {

        String[] chars = message.split("");

        letter3DBlock = new String[y][x][z];

        int indexCount = 0;

        for (int zAxis = 0; zAxis < letter3DBlock[0][0].length; zAxis++) {

            for (int yAxis = 0; yAxis < letter3DBlock.length; yAxis++) {

                for (int xAxis = 0; xAxis < letter3DBlock[0].length; xAxis++) {

                    if (indexCount < chars.length) {
                        letter3DBlock[yAxis][xAxis][zAxis] = chars[indexCount];
                        indexCount++;
                    } else {
                        letter3DBlock[yAxis][xAxis][zAxis] = "";
                    }
                }
            }
        }
    }
}