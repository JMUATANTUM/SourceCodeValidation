// Basic Concept code to check the compiled code.

 //Verify the crc32 value of classes.dex file in apk, that is, check the integrity of dex file
 public static boolean checkDexCrcValue() {
        String apkPath = BaseApplication.getInstance().getPackageCodePath();
        Long dexCrc = Long.parseLong(QianJinSuoApplication.getInstance().getString(R.string.classesdex_crc));
        try {
            ZipFile zipfile = new ZipFile(apkPath);
            ZipEntry dexentry = zipfile.getEntry("classes.dex");
            Log.i("checkDexCrcValue", "classes.dexcrc=" + dexentry.getCrc());
            if (dexentry.getCrc() == dexCrc) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
  }
  
  public static boolean checkApkSha(){
        String apkPath = QianJinSuoApplication.getInstance().getPackageCodePath();
        MessageDigest msgDigest = null;
        try {

            msgDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = new byte[1024];
            int byteCount;
            FileInputStream fis = new FileInputStream(new File(apkPath));
            while ((byteCount = fis.read(bytes)) > 0){
                msgDigest.update(bytes, 0, byteCount);
            }

            BigInteger bi = new BigInteger(1, msgDigest.digest());
            String sha = bi.toString(16);
            Log.i("checkApkSha", "apk sha=" + sha);
            fis.close();
            if(BaseApplication.getInstance().getString(R.string.apk_sha).equals(sha)){
                return true;
            }
            
            //Here we add a hash value from the blockchain and then compare it.
            
            // Read from blockchain, or a central database.

        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }
