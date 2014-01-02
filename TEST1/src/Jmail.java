
/**
 * @author pdw
 *
 * TODO 发送邮件!
 * 
 */
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.security.spec.X509EncodedKeySpec;

public class Jmail {
    public MimeMessage mimeMsg;  // 要發送的訊息
    private Session session;
    Properties props;
    private boolean needAuth=false;
    
    private String username="";
    private String password="";
    
    private Multipart mp;  // 存放Email的標題內容和物件
    
    public Jmail(String stmp){
        setSmtpHost(stmp);
        createMimeMessage();
    }
    /**
     * 
     * @param hostName
     */
    public void setSmtpHost(String hostName){
        //System.out.println("mail.stmp.host= "+hostName);
        if(props==null){
            props=System.getProperties();
        }
        props.put("mail.smtp.host",hostName);
    }
    
    public boolean createMimeMessage(){
        try{
            //System.out.println("Session begin-----------");
            session=Session.getInstance(props,null);
        }catch(Exception e){
            System.out.println("Session.getInstance faild!"+e);
            return false;
        }
        //System.out.println("MimeMEssage begin---------!");
        try{
            mimeMsg=new MimeMessage(session);
            mp=new MimeMultipart();
            return true;
        }catch(Exception e){
            System.out.println("MimeMessage fiald! "+e.toString());
            return false;
        }
    }
    /**
     * 
     * @param need
     */
    public void setNeedAuth(boolean need){
        //System.out.println(":mail.smtp.auth="+need);
        if(props==null){
            props=System.getProperties();
        }
        if(need){
            props.put("mail.smtp.auth","true");
        }
        else{
            props.put("mail.smtp.auth","false");
        }
    }
    /**
     * 
     * @param name
     * @param pass
     */
    public void setNamePass(String name,String pass){
        username=name;
        password=pass;
    }
    /**
     * 
     * @param mailSubject
     * @return boolean
     */
    public boolean setSubject(String mailSubject){
        //System.out.println("set title begin.");
        try{
            if(!mailSubject.equals("")&&mailSubject!=null){
                mimeMsg.setSubject(mailSubject);
            }
            return true;
        }catch(Exception e){
            System.out.println("set Title faild!");
            return false;
        }
    }
    /**
     *  添加附件..
     * @param filename
     * @return
     */
    public boolean addFileAffix(String filename){
        System.out.println("增加附件..");
        if(filename.equals("")||filename==null){
            return false;
        }
        String file[];
        file=filename.split(";");
        System.out.println("你有 "+file.length+" 個附件!");
        try{
            for(int i=0;i<file.length;i++){
                BodyPart bp=new MimeBodyPart();
                FileDataSource fileds=new FileDataSource(file[i]);
                bp.setDataHandler(new DataHandler(fileds));
                bp.setFileName(fileds.getName());
                mp.addBodyPart(bp);
             }
             return true;
           }catch(Exception e){
             System.err.println("增加附件: "+filename+"--faild!"+e);
             return false;
        }
   }
    /**
     * 
     * @param from
     * @return
     */
    public boolean setFrom(String from){
        //System.out.println("Set From .");
        try{
            mimeMsg.setFrom(new InternetAddress(from));
            return true;
        }catch(Exception e){
            return false;
        }
    }
    /**
     * 
     * @param to
     * @return
     */
    public boolean setTo(String to){
        //System.out.println("Set to.");
        if(to==null||to.equals("")){
            return false;
        }
        try{
            mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public boolean setCopyTo(String copyto){
        if(copyto.equals("")||copyto==null){
            return false;
        }
        try{
            String copy[];
            copy=copyto.split(";");
            for(int i=0;i<copy.length;i++){
                mimeMsg.setRecipients(Message.RecipientType.TO,(Address[])InternetAddress.parse(copy[i]));
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    
    /**
     * 設置信件内容!
     * @param mailBody
     * @return boolean
     */
    public boolean setBody(String mailBody){
        try{
            BodyPart bp=new MimeBodyPart();
            //mailBody = java.net.URLEncoder.encode(mailBody,"UTF-8");
            //utf-8
            bp.setContent("<meta http-equiv=Context-Type context=text/html;charset=gb2312>"+mailBody,"text/html;charset=UTF-8");
            mp.addBodyPart(bp);
            return true;
        }catch(Exception e){
            System.out.println("Set context Faild! "+e);
            return false;
        }
    }
    /**
     * 
     * @param htmlpath
     * @return boolean
     */
    public boolean setHtml(String htmlpath){
        try{
            if(!htmlpath.equals("")||htmlpath!=null){
                BodyPart mbp=new MimeBodyPart();
                DataSource ds=new FileDataSource(htmlpath);
                mbp.setDataHandler(new DataHandler(ds));
                mbp.setHeader("Context-ID","meme");
                mp.addBodyPart(mbp);
                }
                return true;
            }catch(Exception  e){
                System.err.println("Set Html Faild!"+e);
                return false;
            }
        }
     public boolean setOut(){
         try{
             mimeMsg.setContent(mp);
             mimeMsg.saveChanges();
             Session mailSession=session.getInstance(props,null);
             Transport tp=mailSession.getTransport("smtp");
             tp.connect((String)props.getProperty("mail.stmp.host"),username,password);
             tp.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO));
             //tp.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.CC));
             //System.out.println("Send Mail 成功..");
             tp.close();
             return true;
         }catch(Exception e){
             e.printStackTrace();
             return false;
         }
     }
     
     public static void main(String[] args)
     {
    	 
    	 Jmail sm=new Jmail("smtp.gmail.com");
    	 // SendMail sm=new SendMail("mail.ldtec.com");
    	 //enable startttls
    	sm.props.put("mail.smtp.starttls.enable", "true");
    	 // 寄信者的信箱帳密
    	 sm.setNamePass(" largeteam8@gmail.com","largeeight");
         
         // 主旨(可中文)
         sm.setSubject("TMD");
         
         // 寄信人
         sm.setFrom("largeteam8@gmail.com");
         
         // 收件人
         sm.setTo("zxc10806@yahoo.com.tw");
         
         // 疑似寄檔案函式，未測試
         //sm.addFileAffix("f:/adsl.txt");
         
         // 不知有何用途，先註解
         //StringBuffer bs=new StringBuffer();
         //bs.append("裴德万:\n");
         //bs.append("       测试度奇珍异宝埼地在檌!!!!!!!!!!!");
         String content = new String();
         content+="哈囉！！<br>";
         content+="你好:)<br>";
         content+="<br>";
         
         // 信件內容(打中文，寄出後為問號)
         //String nn = new String(content);
          
         sm.setBody(content);
      
       // sm.setContent(nn, "text/html;charset=UTF-8");
         sm.setNeedAuth(true);
         
         // 寄信
         boolean b=sm.setOut();
         
         if(b){
             System.out.println("\n邮件发送成功!!!!!");
         }
         else{
             System.out.println("邮件发送失败!!!!");
         }
     
     }
     
       
}