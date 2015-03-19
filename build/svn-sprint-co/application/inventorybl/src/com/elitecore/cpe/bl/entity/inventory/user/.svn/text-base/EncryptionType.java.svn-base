package com.elitecore.cpe.bl.entity.inventory.user;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries({
	@NamedQuery(name="EncryptionType.findById",query="select o from EncryptionType o  where o.encryptionTypeId = :encryptionTypeId")
})
@Table(name="TBLSENCRYPTIONTYPE"
)
public class EncryptionType  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String encryptionTypeId;
     private String name;
     private String alias;
//     private Set<User> users = new HashSet<User>(0);

    public EncryptionType() {
    }

	
    public EncryptionType(String encryptionTypeId, String name, String alias) {
        this.encryptionTypeId = encryptionTypeId;
        this.name = name;
        this.alias = alias;
    }
/*    public EncryptionType(String encryptionTypeId, String name, String alias, Set<User> users) {
       this.encryptionTypeId = encryptionTypeId;
       this.name = name;
       this.alias = alias;
       this.users = users;
    }*/
   
     @Id 
    @Column(name="ENCRYPTIONTYPEID")
    public String getEncryptionTypeId() {
        return this.encryptionTypeId;
    }
    
    public void setEncryptionTypeId(String encryptionTypeId) {
        this.encryptionTypeId = encryptionTypeId;
    }

    
    @Column(name="NAME")
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="ALIAS")
    public String getAlias() {
        return this.alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }

/*@OneToMany(fetch=FetchType.LAZY, mappedBy="encryptionType")
    public Set<User> getUsers() {
        return this.users;
    }
    
    public void setUsers(Set<User> users) {
        this.users = users;
    }*/




}


