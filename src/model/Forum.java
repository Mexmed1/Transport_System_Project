package model;
import javafx.collections.FXCollections;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Forum {
    private int id;
    private String title;
    private List<Comment> commentList;



    public Forum(int id, String title) {
        this.id=id;
        this.title = title;
        this.commentList = FXCollections.observableArrayList();
    }
    public void updateTitle(String newTitle) {
        this.title = newTitle;
        this.commentList = FXCollections.observableArrayList();
    }
    public String toString(){
        return title;
    }
}
