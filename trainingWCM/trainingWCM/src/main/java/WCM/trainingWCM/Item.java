package WCM.trainingWCM;


import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int rating;
    //private String color;

    public Item() {}

    Item(String name, double price, int rating) {
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    Item(Long id, String name, double price, int rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        if(price >= 0) {
            this.price = price;
        }
    }

    public int getRating(){
        return this.rating;
    }

    public void setRating(int rating){
        if (rating >= 5) {
            this.rating = 5;
        } else if (rating <= 1) {
            this.rating = 1;
        } else {
            this.rating = rating;
        }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Item))
            return false;
        Item item = (Item) o;
        return Objects.equals(this.id, item.id) && Objects.equals(this.name, item.name)
                && Objects.equals(this.rating, item.rating) && Objects.equals(this.price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.price, this.rating);
    }

    @Override
    public String toString() {
        return "Item{ " + "ID=" + this.id + ", Name='" + this.name + '\'' + ", Price='" + this.price
                + '\'' + ", Rating='" + this.rating + '\'' + " }";
    }

}
