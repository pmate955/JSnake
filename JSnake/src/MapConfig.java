public interface MapConfig {

    enum Cell {
        EMPTY,
        HEAD,
        FOOD,
        UP,
        DOWN,
        LEFT,
        RIGHT;

        public boolean isDirection() {
            return (this == UP || this == DOWN || this == LEFT || this == RIGHT);
        }
    }
}
