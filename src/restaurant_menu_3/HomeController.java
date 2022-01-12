/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant_menu_3;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalTime; 
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import static restaurant_menu_3.ReceiptController.Total_Price;



/**
 * FXML Controller class
 *
 * @author Youssef Al-lehyani
 */
public class HomeController implements Initializable {
     Parent SearchRoot ;
     Scene scene;
     Stage stage;
     ReceiptController receipt_Controller;
     Stage ReceiptStage;
     

    
     static int i = 0 ;
        private Connection connection;
           private static final String URL = "jdbc:derby://localhost:1527/restaurant_Project";
    private ObservableList<String>Dish_price = FXCollections.observableArrayList();
    static ObservableList<Integer>ID = FXCollections.observableArrayList();
    static ObservableList<String>Dish_Names=FXCollections.observableArrayList();
    private ObservableList<Group>groupList = FXCollections.observableArrayList();
    
     ObservableList<String>Dish_price_Breakfast = FXCollections.observableArrayList();
    static ObservableList<Integer>ID_Breakfast = FXCollections.observableArrayList();
    static ObservableList<String>Dish_Names_Breakfast=FXCollections.observableArrayList();
    static ObservableList<String>dish_order_name = FXCollections.observableArrayList();
    SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10,1);
    ObservableList<String> list = FXCollections.observableArrayList("Breakfast","Lunch or Diner");
     ObservableList<Integer> Table_Number = FXCollections.observableArrayList(1,2,3,4,5,6,7);
    
    
    static ObservableList<Integer>Total_price = FXCollections.observableArrayList();
    
    
 @FXML private javafx.scene.control.Button closeButton;
    @FXML
    private ListView  dishes;
    @FXML
    private ComboBox  Meals_type;
    @FXML
    private  ComboBox  Table;
    @FXML
    private Pane PricePane;
    @FXML
    private Spinner disheQuantity;
//    private Text text1;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        Meals_type.setItems(list);
        Table.setItems(Table_Number);
        disheQuantity.setValueFactory(svf);
        SQL(URL);
    }    
    public void SQL(String URL){
                        try {
             connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();
            String select = "SELECT * FROM DISHES";
            String Breakfast = "SELECT * FROM BREAKFAST_DISHES";
            String selec_sql = "SELECT count(*) FROM DISHES";
            ResultSet result = statement.executeQuery(selec_sql);
            ResultSetMetaData metaData = result.getMetaData();
            result.next(); 
            ResultSet resultA = statement.executeQuery(select);
            int numberOfColumns = metaData.getColumnCount();                        
            for (int i = 1; i <= numberOfColumns; i++) {
           	while(resultA.next()) { 
                    int id = resultA.getInt("ID"); String name = resultA.getString("Dish_Name"); String price = resultA.getString("Dish_price");
                    Dish_price.add(price);
                    Dish_Names.add(name);
                    ID.add(id);
                }  
            }
                        String Breakfast_sql = "SELECT count(*) FROM BREAKFAST_DISHES";                        
                        ResultSet Breakfast_result = statement.executeQuery(Breakfast_sql);
                        ResultSetMetaData metaDataBreakfast = Breakfast_result.getMetaData();                       
                        ResultSet resultBreakfast = statement.executeQuery(Breakfast);
                         int BreakfastColumns = metaDataBreakfast.getColumnCount();
            for (int i = 1; i <= BreakfastColumns; i++) {
           	while(resultBreakfast.next()) { 
                    int id = resultBreakfast.getInt("ID"); String name = resultBreakfast.getString("Dish_Name"); String price = resultBreakfast.getString("Dish_price");
                    Dish_price_Breakfast.add(price);
                    Dish_Names_Breakfast.add(name);
                    ID_Breakfast.add(id);
                } 
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    @FXML
    private void Meals(ActionEvent event) {
        String s = Meals_type.getSelectionModel().getSelectedItem().toString();
        if(s.equalsIgnoreCase("Lunch or Diner")){
            Lunch_Diner();
    }else if(s.equalsIgnoreCase("Breakfast"))
        Breakfast();
    }
    @FXML
    private void cancel(ActionEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        this.stage.close();
    
    }

    @FXML
    private void Add(ActionEvent event) throws IOException { 
        if (i >0){
            if(this.stage.isShowing())
                addNewDishe();
            else
                if(!(out_time()))
                    sowingStage(this.stage, this.scene); 
        }else{
            receiptPage();
            
        }
        receipt_Controller.AddTotalPtrice();      
    }
    private void Lunch_Diner(){
            PricePane.getChildren().retainAll(PricePane);
            dishes.setItems(Dish_Names);
            int y = 0;
            for(int i = 0 ; Dish_price.size()>i;i++){
              String dish_price = Dish_price.get(i);
              String[] arrOfStr = dish_price.split(" ",3);
              Text text = new Text();
              text.setText(Dish_price.get(i));
                    text.setX(5.0); 
                    text.setY(20.0+y);
                    text.setFill(Color.WHITE);
                    y =y+30;
                    PricePane.getChildren().add(text);
                
            }

}
    private void Breakfast(){
        PricePane.getChildren().retainAll(PricePane);
        dishes.setItems(Dish_Names_Breakfast);
        int y = 0;
        for(int i = 0 ; Dish_price_Breakfast.size()>i;i++){
            String dish_price = Dish_price_Breakfast.get(i);
            String[] arrOfStr = dish_price.split(" ",3);
            Text text = new Text();
            text.setText(Dish_price_Breakfast.get(i));
            text.setFill(Color.WHITE);
            text.setX(5.0); 
            text.setY(20.0+y);
                y =y+30;
            PricePane.getChildren().add(text);

            }

    }
    private void receiptPage() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Receipt.fxml"));      
        Parent root = loader.load();   
        receipt_Controller = loader.getController();        
        this.stage = new Stage();
        this.scene = new Scene(root);
        this.scene.getStylesheets().add("/styles/Style.css");
        this.stage.setTitle("Receipt"); 
        addNewDishe();
        i++;
    }
    private void sowingStage(Stage stage, Scene scene){
        stage.setScene(scene);
        stage.show();
    }
    private void addNewDishe(){
        try{
        int index = dishes.getSelectionModel().getSelectedIndex();
        String thedishe = dishes.getItems().get(index).toString();
            if(out_time()){
                String s = "sorry,this is out of time";
                dish_exists(s);
            }else{
                String s =PutDish(index); 
                if(s.equalsIgnoreCase("Lunch or Diner")){
                    Lunch_DinerPrice(index);
                }else if(s.equalsIgnoreCase("Breakfast"))
                    Breakfastprice(index);
                }
        }catch(Exception erorr){
            sowingStage(this.stage, this.scene);
        }     
    }
    private void Breakfastprice(int index){
        String dish_price = Dish_price_Breakfast.get(index);
        Price(dish_price); 
    }
    private void Lunch_DinerPrice(int index){
        String dish_price = Dish_price.get(index);
        Price(dish_price); 
    }
    private boolean out_time(){
            LocalTime myObj = LocalTime.now();

            int Hour = myObj.getHour();
            if(!(Hour>=0&&Hour<8)){
                return false;
            }
            return true;       
    }
    private boolean out_of_dinnerTime(){
            LocalTime myObj = LocalTime.now();

            int Hour = myObj.getHour();
            if((Hour>=8&&Hour<12)){
                return false;
            }
            return true;
    }
    public static void dish_exists(String massage){
            Text t = new Text ();
            t.setText(massage);
            t.setX(80);
            t.setY(80);
            Group root = new Group(t);         
            Scene scene = new Scene(root,400, 250);
            Stage TStage = new Stage();
        
            TStage.setTitle("massage");
            TStage.setScene(scene);
            TStage.show();
    }
    private String PutDish(int index){
        String MealsType = Meals_type.getSelectionModel().getSelectedItem().toString();
        boolean check_Lunch_Diner =out_of_dinnerTime()&& MealsType.equalsIgnoreCase("Lunch or Diner");
        boolean check_Breakfast = !(out_of_dinnerTime())&& MealsType.equalsIgnoreCase("Breakfast");
        if(check_Lunch_Diner){
            return check_Time(index);
        }else if(check_Breakfast) {
            return check_Time(index);
        }else{
            if(!(check_Lunch_Diner)&&  MealsType.equalsIgnoreCase("Lunch or Diner") ){
            String massage = "Time of breakfast";
            dish_exists(massage);
            }else{
            String massage = "Time of Lunch or Diner";
            dish_exists(massage);
        }
        }
        
         return null;
    }
    private String check_Time(int index){
        
        String MealsType = Meals_type.getSelectionModel().getSelectedItem().toString();
        sowingStage(this.stage, this.scene);
        dish_order_name.add(dishes.getItems().get(index).toString());
        receipt_Controller.ListOfOrders.setItems(dish_order_name);
        receipt_Controller.DishsOrder.add(dishes.getItems().get(index).toString());
        return  Meals_type.getSelectionModel().getSelectedItem().toString();
    }
    public String getDish_price(int i){
        return this.Dish_price.get(i);
    }
    public void Price(String dish_price){
            Text text = new Text();
            String[] arrOfStr = dish_price.split(" ",3);
            Integer priceTotal = (Integer.parseInt(arrOfStr[0])*Integer.parseInt(disheQuantity.getValue().toString()));
            text.setText(priceTotal.toString()+" $");
            text.setX(5.0); 
            text.setY(20.0+receipt_Controller.ylable);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
            receipt_Controller.getDisheprice().getChildren().add(text);
            receipt_Controller.set_total_price(priceTotal);
            receipt_Controller.price_par_Dish(arrOfStr[0]);
            Total_price.add(priceTotal);
            Text NumberDish = new Text();
            NumberDish.setText(disheQuantity.getValue().toString());
            NumberDish.setX(5.0); 
            NumberDish.setY(20.0+receipt_Controller.ylable);
            NumberDish.setFill(Color.WHITE);
            NumberDish.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
            receipt_Controller.ylable +=30;
            receipt_Controller.getNumberDish().getChildren().add(NumberDish);
            receipt_Controller.setQuantity(Integer.parseInt(disheQuantity.getValue().toString()));
    }

}
    
