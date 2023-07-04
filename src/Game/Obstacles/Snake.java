package Game.Obstacles;

public class Snake extends Obstacle{
    public Snake() {
        super(4, "Yılan", generateRandomDamage(), 12, 0);
    }

    private static int generateRandomDamage() {
        // Rastgele olarak 3 ile 6 arasında bir hasar değeri oluşturulur
        return (int) (Math.random() * 4) + 3;
    }

    @Override
    public int getLoot() {
        // Yılanın para yerine eşya kazanma ihtimali olduğu belirtilmiş
        // Ancak bu özellik Obstacle sınıfında "loot" olarak tanımlanmış olduğu için, burada bir değişiklik yapmamıza gerek yok.
        return super.getLoot();
    }
}
