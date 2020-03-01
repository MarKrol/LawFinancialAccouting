package pl.camp.it.model.userLogin;

import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.preschooler.Preschooler;

import javax.persistence.*;

@Entity(name="tparentLogin")
public class ParentLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String pass;
    @OneToOne
    @JoinColumn(name = "preschoolerId")
    Preschooler preschooler;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Preschooler getPreschooler() {
        return preschooler;
    }

    public void setPreschooler(Preschooler preschooler) {
        this.preschooler = preschooler;
    }

    public String getLogin() {
        return login;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
