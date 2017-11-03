package ch.bbcag.javaee.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(schema = "gamexchange", name = "item")
@NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i")
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "ITEM_ID_GENERATOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_ID_GENERATOR")
    private Long id;

    private String description;

    private String imageLocation;

    private Double price;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sold;

    // bi-directional many-to-one association to Customer
    @ManyToOne
    private Customer seller;

    // bi-directional many-to-one association to Customer
    @ManyToOne
    private Customer buyer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getSold() {
        return sold;
    }

    public void setSold(Date sold) {
        this.sold = sold;
    }

    public Customer getSeller() {
        return seller;
    }

    public void setSeller(Customer seller) {
        this.seller = seller;
    }

    public Customer getBuyer() {
        return buyer;
    }

    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }

    @Override
    public int hashCode() {
        final int prime = 31;

        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Item)) {
            return false;
        }
        Item other = (Item) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return id + "-" + title + "-" + seller;
    }
}
