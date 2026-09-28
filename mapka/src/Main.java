import java.util.*;

public class Main {
    // Prosta reprezentacja pola na mapie
    static class Pole {
        int x, y;
        boolean przeszkoda; // czy pole jest oznaczone 'x'

        Pole(int x, int y, boolean przeszkoda) {
            this.x = x;
            this.y = y;
            this.przeszkoda = przeszkoda;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pole)) return false;
            Pole pole = (Pole) o;
            return x == pole.x && y == pole.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static List<Pole> findPath(Pole start, Pole end, Set<Pole> przeszkody) {
        Queue<Pole> queue = new LinkedList<>();
        Map<Pole, Pole> cameFrom = new HashMap<>();
        Set<Pole> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Pole current = queue.poll();

            if (current.equals(end)) {
                // Odtwórz ścieżkę
                return reconstructPath(cameFrom, current);
            }

            // Sprawdź sąsiadów (góra, dół, lewo, prawo)
            int[] dx = {0, 0, 1, -1};
            int[] dy = {1, -1, 0, 0};

            for (int i = 0; i < 4; i++) {
                Pole neighbor = new Pole(current.x + dx[i], current.y + dy[i], false);

                // Jeśli sąsiad istnieje, nie jest przeszkodą i nie został odwiedzony
                if (!przeszkody.contains(neighbor) && !visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }
        return new ArrayList<>(); // Brak ścieżki
    }

    private static List<Pole> reconstructPath(Map<Pole, Pole> cameFrom, Pole current) {
        List<Pole> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        path.add(current); // Dodaj start
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Pole start = new Pole(0, 0, false);
        Pole end = new Pole(3, 3, false);

        // Utwórz zbiór przeszkód (pola oznaczone 'x')
        Set<Pole> przeszkody = new HashSet<>();
        przeszkody.add(new Pole(1, 1, true));
        przeszkody.add(new Pole(2, 2, true));

        List<Pole> path = findPath(start, end, przeszkody);
        System.out.println("Znaleziona ścieżka: " + path);
    }
}