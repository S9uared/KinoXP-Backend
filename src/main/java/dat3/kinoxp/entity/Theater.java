package dat3.kinoxp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Theater {

    @Id
    private int id;

    @Column(name="size", nullable = false)
    private int size;

    //Implement after merge with showing
    /*
    @OneToMany(mappedBy = "car", cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    List<Showing> showings;

    public void addShowing(Showing showing){
        if(showings == null){
            showings = new ArrayList<>();
        }
        showings.add(showing);
    }
    */

    public Theater(int id, int size) {
        this.id = id;
        this.size = size;
    }
}
