/*  1:   */ package com.blog.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/*  2:   */ @Table(name="t_blogger")
/*  3:   */ public class Blogger
/*  4:   */ {
    @Id
/*  5:   */   private Integer id;
/*  6:   */   private String userName;
/*  7:   */   private String password;
/*  8:   */   private String nickName;
/*  9:   */   private String sign;
/* 10:   */   private String profile;
/* 11:   */   private String imageName;
/* 12:   */
/* 13:   */   public Integer getId()
/* 14:   */   {
/* 15:19 */     return this.id;
/* 16:   */   }
/* 17:   */
/* 18:   */   public void setId(Integer id)
/* 19:   */   {
/* 20:22 */     this.id = id;
/* 21:   */   }
/* 22:   */
/* 23:   */   public String getUserName()
/* 24:   */   {
/* 25:25 */     return this.userName;
/* 26:   */   }
/* 27:   */
/* 28:   */   public void setUserName(String userName)
/* 29:   */   {
/* 30:28 */     this.userName = userName;
/* 31:   */   }
/* 32:   */
/* 33:   */   public String getPassword()
/* 34:   */   {
/* 35:31 */     return this.password;
/* 36:   */   }
/* 37:   */
/* 38:   */   public void setPassword(String password)
/* 39:   */   {
/* 40:34 */     this.password = password;
/* 41:   */   }
/* 42:   */
/* 43:   */   public String getNickName()
/* 44:   */   {
/* 45:37 */     return this.nickName;
/* 46:   */   }
/* 47:   */
/* 48:   */   public void setNickName(String nickName)
/* 49:   */   {
/* 50:40 */     this.nickName = nickName;
/* 51:   */   }
/* 52:   */
/* 53:   */   public String getSign()
/* 54:   */   {
/* 55:43 */     return this.sign;
/* 56:   */   }
/* 57:   */
/* 58:   */   public void setSign(String sign)
/* 59:   */   {
/* 60:46 */     this.sign = sign;
/* 61:   */   }
/* 62:   */

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }



    /* 73:   */   public String getImageName()
/* 74:   */   {
/* 75:55 */     return this.imageName;
/* 76:   */   }
/* 77:   */
/* 78:   */   public void setImageName(String imageName)
/* 79:   */   {
/* 80:58 */     this.imageName = imageName;
/* 81:   */   }

    @Override
    public String toString() {
        return "Blogger{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sign='" + sign + '\'' +
                ", proFile='" + profile + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
    /* 82:   */ }




/* Location:           D:\classes\

 * Qualified Name:     com.blog.entity.Blogger

 * JD-Core Version:    0.7.0.1

 */