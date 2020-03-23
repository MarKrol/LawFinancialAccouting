package pl.camp.it.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.preschooler.Preschooler;

@Component
@SessionScope
public class SessionObject {
    private boolean logged;
    private Employee employee;
    private Preschooler preschooler;
    private String sendData;

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String getSendData() {
        return sendData;
    }

    public void setSendData(String sendData) {
        this.sendData = sendData;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Preschooler getPreschooler() {
        return preschooler;
    }

    public void setPreschooler(Preschooler preschooler) {
        this.preschooler = preschooler;
    }
}
