package model;
import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public Customer(String firstName,String lastName,String email) {
        email=email.trim();
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email Address format");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email.toLowerCase();
    }

    private boolean isValidEmail(String email){
        if(email == null) return false;
        return Pattern.matches(EMAIL_PATTERN,email);
    }
    public String getEmail(){
        return email;
    }

    @Override
    public boolean equals(Object obj){
        if(this==obj)return true;
        if(!(obj instanceof Customer)) return false;
        Customer other =(Customer) obj;
        return email.equals(other.email);
    }

    @Override
    public int hashCode(){
        return email.hashCode();
    }

    @Override
    public String toString(){
        return "Customer: "+ firstName +" "+ lastName +" Email: "+ email;
    }
}
