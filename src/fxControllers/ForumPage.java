package fxControllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Comment;
import model.Forum;
import utils.DbUtils;
import utils.fxUtils;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ForumPage implements Initializable {


    @FXML
    public ListView<Forum> forumListView;
    @FXML
    public TreeView<Comment> commentTree;
    @FXML
    public TextArea commentBody;
    @FXML
    public MenuItem addNewTitlesOnForum;
    @FXML
    public MenuItem deleteTitlesOnForum;
    @FXML
    public MenuItem updateTitlesOnForum;
    @FXML
    public ListView<Forum> forumPageView;


    private Comment selectedComment = null;

    public void updateTitlesOnForum() throws SQLException  {
        Connection connection = DbUtils.connectToDb();
        Forum selectedForum = forumPageView.getSelectionModel().getSelectedItem();
        if (selectedForum != null) {

            try {
                String UPDATE_FORUM = "UPDATE forum SET title = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FORUM);
                preparedStatement.setString(1, commentBody.getText());
                preparedStatement.setInt(2, selectedForum.getId());
                preparedStatement.executeUpdate();
                DbUtils.disconnect(connection, preparedStatement);
                fxUtils.throwAlert("Updating forum title", " Successfully updated");
                forumPageView.setItems(DbUtils.getDataForum());
                System.out.println("fsadasdas");
            } catch (Exception e) {
                fxUtils.throwAlert("Data format", "Warning");
            }



        }
    }

    public void addNewTitlesOnForum() throws SQLException {
        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO forum(title) VALUES (?)");
        preparedStatement.setString(1,commentBody.getText());
        preparedStatement.execute();
        DbUtils.disconnect(connection,preparedStatement);
        forumPageView.setItems(DbUtils.getDataForum());

    }

    public void deleteTitlesOnForum() throws SQLException {
        Connection connection = DbUtils.connectToDb();
        Forum selectedForum = forumPageView.getSelectionModel().getSelectedItem();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM forum WHERE id=?");
        preparedStatement.setInt(1,selectedForum.getId());
        preparedStatement.executeUpdate();
        DbUtils.disconnect(connection,preparedStatement);
        forumPageView.setItems(DbUtils.getDataForum());
    }

    public void addComment() {

        Forum selectedForum = forumPageView.getSelectionModel().getSelectedItem();
        if (selectedForum == null) {
            return;
        }

        TreeItem<Comment> selectedCommentTreeItem = commentTree.getSelectionModel().getSelectedItem();
        if (selectedCommentTreeItem != null) {
            Comment parentComment = selectedCommentTreeItem.getValue();
            Comment childComment = new Comment(commentBody.getText());
            parentComment.addChildComment(childComment);


            selectedCommentTreeItem.getChildren().add(new TreeItem<>(childComment));
        } else {

            Comment newComment = new Comment(commentBody.getText());
            selectedForum.getCommentList().add(newComment);


            refreshCommentTree(selectedForum);
        }
    }

    public void goToComments() {
        Forum selectedForum = forumPageView.getSelectionModel().getSelectedItem();
        if (selectedForum != null) {
            refreshCommentTree(selectedForum);
        }
    }

    public void selectComment() {

        selectedComment = commentTree.getSelectionModel().getSelectedItem().getValue();
    }

    // Helper method to refresh the TreeView with the comments for the selected forum
    private void refreshCommentTree(Forum forum) {
        if (forum == null) {
            return;
        }

        TreeItem<Comment> root = new TreeItem<>(new Comment("Comments for " + forum.getTitle()));


        ObservableList<Comment> comments = (ObservableList<Comment>) forum.getCommentList();

        for (Comment comment : comments) {
            TreeItem<Comment> commentNode = new TreeItem<>(comment);

            // Add each child comment as a child node of its parent comment node
            for (Comment childComment : comment.getChildComments()) {
                TreeItem<Comment> childCommentNode = new TreeItem<>(childComment);
                commentNode.getChildren().add(childCommentNode);
            }

            root.getChildren().add(commentNode);
        }


        commentTree.setRoot(root);
    }

    public void updateComment() {
        TreeItem<Comment> selectedCommentTreeItem = commentTree.getSelectionModel().getSelectedItem();
        if (selectedCommentTreeItem != null) {
            selectedCommentTreeItem.setValue(new Comment(commentBody.getText()));
            commentTree.refresh();
        }
    }

    public void deleteComment() {
        ObservableList<Integer> selectedCommentIndices = commentTree.getSelectionModel().getSelectedIndices();
        for (int i = selectedCommentIndices.size() - 1; i >= 0; i--) {
            int index = selectedCommentIndices.get(i);
            TreeItem<Comment> item = commentTree.getTreeItem(index);
            if (item != null) {
                if (item.getParent() == null) {
                    deleteComment(item);
                } else {
                    item.getParent().getChildren().remove(item);
                }
            }
        }
        commentTree.refresh();
    }

    private void deleteComment(TreeItem<Comment> comment) {
        if (comment == null) {
            return;
        }
        if (comment.getParent() != null) {
            for (TreeItem<Comment> child : comment.getChildren()) {
                deleteComment(child);
            }
            comment.getParent().getChildren().remove(comment);
        } else {
            commentTree.getRoot().getChildren().remove(comment);
        }
        commentTree.refresh();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            forumPageView.setItems(DbUtils.getDataForum());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}