package fbhackathon.com.tube.MapData;

import java.util.List;
import java.util.NoSuchElementException;

public class Line {

    private final String name;
    private final List<Station> stations;

    public Line(String name, List<Station> stations) {
        this.name = name;
        this.stations = stations;
    }

    public Station findStation(String name) throws NoSuchElementException {
        for (Station s : stations) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        throw new NoSuchElementException();
    }

    public Station getNext(Station station, boolean direction)
            throws NoSuchElementException, IllegalArgumentException{
        int idx = stations.indexOf(station);
        if (idx == -1) {
            throw new IllegalArgumentException();
        } else {
            if (direction) {
                idx++;
            } else {
                idx--;
            }
            if (idx >= 0 && idx < stations.size()) {
                return stations.get(idx);
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        return name.equals(line.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" Line:");
        for (Station s : stations) {
            sb.append(s.toString());
            sb.append(", ");
        }
        return sb.toString();
    }
}