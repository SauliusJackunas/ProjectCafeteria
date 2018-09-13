package lt.baltic.talents.project.cafeteria;

public enum Employee {

    JURIJUS("Jurijus Karmazinas"),
    AISTE("Aiste Aistauckaite"),
    GIEDRIUS("Giedrius Racibara");

    private String name;

    private Employee(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
