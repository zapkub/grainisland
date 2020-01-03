/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.util;

/**
 *
 * @author Varavut
 * คลาสสำหรับเก็บค่า error
 */
public class StatusMessage {
    private boolean error;//ตัวแปรที่ใช้เก็บว่าเกิด error ไหม
    private String message;//ตัวแปรที่เก็บรายละเอียดของ error

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
        if(!error)
        {
            message=null;
        }
    }

    public StatusMessage()
    {
        this.error=false;
        this.message="";
    }

    public StatusMessage(boolean isError,String errorDetail)
    {
        this.error=isError;
        this.message=errorDetail;
    }

    public StatusMessage(String errorDetail)
    {
        this.error=true;
        this.message=errorDetail;
    }

    public StatusMessage(boolean isError)
    {
        this.error=isError;
        this.message="";
    }

}
