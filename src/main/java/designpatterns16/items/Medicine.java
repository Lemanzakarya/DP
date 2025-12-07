package designpatterns16.items;

//Item subclass representing medicinal products.
//drugform (e.g., tablet, capsule, syrup).
public class Medicine extends Consumable {
    private String drugForm;

    public Medicine(String name, String type, Double pricePerUnit, String drugType) {
        super(name, type, pricePerUnit);
        this.drugForm = drugType;
    }

    public String getDrugForm() {
        return drugForm;
    }

    public void setDrugForm(String drugForm) {
        this.drugForm = drugForm;
    }

    @Override
    public String toString() {
        return "Medicine{" +
               "name='" + name + '\'' +
               ", type='" + type + '\'' +
               ", pricePerUnit=" + pricePerUnit +
               ", drugForm='" + drugForm + '\'' +
               '}';
    }
}
