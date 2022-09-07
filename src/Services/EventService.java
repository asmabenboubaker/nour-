/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Blog;
import Utils.MyDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author macbook
 */
public class EventService implements IService<Blog> {

    Connection con;
    Statement stm;

    public EventService() {
        con = MyDB.getInstance().getCon();
    }

    @Override
    public void ajouter(Blog t) throws SQLException {
        String req = "INSERT INTO `blog` (`nom`, `descri`,`date`) VALUES ( '"
                + t.getNom() + "', '" + t.getPrenom() + "', " + t.getDate()+ ") ";
        stm = con.createStatement();
        stm.executeUpdate(req);

    }

    @Override
    public void ajouterr(Blog t) throws SQLException {
        String req = "INSERT INTO `blog` (`nom`, `descri`,`date`,`categorie_id`,`image`) VALUES (?,?,?,?,?)";
        
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        PreparedStatement pstm = con.prepareStatement(req);
        pstm.setString(1, t.getNom());
        pstm.setString(2, t.getPrenom());
        pstm.setDate(3, t.getDate());
    pstm.setInt(4, t.getCat());
         pstm.setString(5, t.getImage());
        pstm.executeUpdate();
           
    }

    @Override
    public List<Blog> afficher() throws SQLException {
        String req = "Select * from `blog`";
        stm = con.createStatement();
        ResultSet rst = stm.executeQuery(req);
        System.out.println(rst.toString());
        List<Blog> personnes = new ArrayList<>();
        while(rst.next()){
            
            Blog p = new Blog(rst.getInt("id"),rst.getString("nom"),rst.getString(3),rst.getDate("date"),rst.getInt("categorie_id"),rst.getString("image"));
            personnes.add(p);
            
        
        }
        System.out.println("nourrrrrrrrrr"+personnes);
        
        return personnes;
        
    }
    public void delete(int id) throws SQLException{
          String sql = "DELETE FROM blog WHERE id=?";
 
            PreparedStatement statement = con.prepareStatement(sql);
          statement.setLong(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println(" deleted successfully!");
}
      }
     public void update(Blog t) throws SQLException {
          //String req="UPDATE personne SET nom='"+t.getNom()+"',prenom='"+t.getPrenom()+"'WHERE id="+t.getId();
            String req="UPDATE blog SET nom='"+t.getNom()+"',descri='"+t.getPrenom() +"',date='"+t.getDate()+"',categorie_id='"+t.getCat() +"'WHERE id="+t.getId();
       // String req = "UPDATE `personne` SET `nom`=?, `prenom`=? WHERE id=&id";
        PreparedStatement pstm = con.prepareStatement(req);
       
       int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println(" update successfully!");
}
            else{
                System.out.println(" failed!");
            }
    }
      
        
    public List<Blog> recherche(String searched) {

        List<Blog> lista = new ArrayList<>();
        try {

            String req = "select * from blog WHERE nom LIKE '%" + searched + "%' ;";
            PreparedStatement pst = con.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            Blog p = new Blog();
            while (rs.next()) {

                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("descri"));
                
                p.setDate(rs.getDate("date"));
                 p.setCat(rs.getInt("id_categorie"));

                lista.add(p);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println("Services.GuideService.recherche()" + ex.getMessage());
        }
        return null;

    } 
     

}
