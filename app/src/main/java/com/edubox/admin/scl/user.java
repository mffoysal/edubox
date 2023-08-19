package com.edubox.admin.scl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import com.edubox.admin.ImageUtil;
import com.edubox.admin.db.DBConnection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class user {

    private Connection con = null;
    private PreparedStatement pst = null, pstt=null, pstaY=null;
    private ResultSet rs = null, rsp=null;
    private String sql,sqlu,sqlaY;

    protected int id,aStatus,uType;
    protected String sId,tId,uId,tName,tPhone,tEmail,tPass,tAddress,tMajor,tBal,tLogo,addDate,nidBirth;
    protected Image image;
    protected File file;
    protected byte[] imgData;
    private String dob;
    private Bitmap profilePic;
    private String sync_key;
    private int sync_status= 0;
    private String uniqueId;
    public void setUniqueId(String uniqueId){
        this.uniqueId=uniqueId;
    }public String getUniqueId(){
        return uniqueId;
    }
    public int getSync_status() {
        return sync_status;
    }
    public void setSync_status(int id) {
        this.sync_status = id;
    }
    public String getSync_key() {
        return sync_key;
    }
    public void setSync_key(String sId) {
        this.sync_key = sId;
    }

    public user() {
        con = DBConnection.ConnectionDB();
    }

    public void setFile(File file) {
        this.file = file;
    }
    public void setaStatus(int aStatus) {
        this.aStatus = aStatus;
    }public void setuType(int uType) {
        this.uType = uType;
    }
    public void settId(String tId) {
        this.tId = tId;
    }public void settName(String tName) {
        this.tName = tName;
    }public void settPhone(String tPhone) {
        this.tPhone = tPhone;
    }public void settPass(String tPass) {
        this.tPass = tPass;
    }public void settAddress(String tAddress) {
        this.tAddress = tAddress;
    }public void settEmail(String tEmail) {
        this.tEmail = tEmail;
    }public void settMajor(String tMajor) {
        this.tMajor = tMajor;
    }public void settBal(String tBal) {
        this.tBal = tBal;
    }public void settLogo(String tLogo) {
        this.tLogo = tLogo;
    }public void setid(int id) {
        this.id = id;
    }public void setnidBirth(String nid) {
        this.nidBirth = nid;
    }public void setuId(String uId) {
        this.uId = uId;
    }public void setsId(String sId) {
        this.sId = sId;
    }public void setAdmissionDate(String addDate)
    {
        this.addDate=addDate;
    }



    public int getid() {
        return id;
    }
    public int getaStatus() {
        return aStatus;
    }public int getuType() {
        return uType;
    }
    public String gettId() {
        return tId;
    }public String gettName() {
        return tName;
    }public String gettPhone() {
        return tPhone;
    }public String gettPass() {
        return tPass;
    }public String gettAddress() {
        return tAddress;
    }public String gettEmail() {
        return tEmail;
    }public String gettMajor() {
        return tMajor;
    }public String gettBal() {
        return tBal;
    }public String gettLogo() {
        return tLogo;
    }public String getnidBirth() {
        return nidBirth;
    }public String getuId() {
        return uId;
    }public String getsId() {
        return sId;
    }




    public File getFile() {
        return file;
    }

    public byte[] getImgData() {

        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int byteRead;
            try {
                while((byteRead = fis.read(buffer)) != -1) {
                    baos.write(buffer, 0, byteRead);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            imgData = baos.toByteArray();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return imgData;
    }

    public String generateAdmissionDate()
    {
        return addDate;
    }
    public String getAdmissionDate()
    {
        return addDate;

    }

    public void setProfilePic(Image image)
    {
        this.image=image;
    }

    public void setProfilePic(byte[] imageData) {
        this.profilePic = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
    }

    public Bitmap getProfilePic() {
        return profilePic;
    }

    public byte[] getProfilePicInBytes() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        profilePic.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public Bitmap getProfilePic(int width, int height) {
        return Bitmap.createScaledBitmap(profilePic, width, height, true);
    }

    public Bitmap getRoundedProfilePic(int width, int height, int radius) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(profilePic, width, height, true);
        return ImageUtil.makeRoundedCorner(scaledBitmap, radius);
    }




    public Date getBirthDateInDateFormat()
    {
        Date date=null;
        try {
            date=new SimpleDateFormat("dd-MM-yyyy").parse(this.dob);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }


    public static void main(String[] args) {


    }

}
