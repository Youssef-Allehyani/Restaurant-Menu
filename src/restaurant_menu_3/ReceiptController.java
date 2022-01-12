/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant_menu_3;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import restaurant_menu_3.HomeController;

/**
 * FXML Controller class
 *
 * @author Youssef Al-lehyani
 */
public class ReceiptController implements Initializable {
    HomeController Home_Controller;
    private static final String URL = "jdbc:derby://localhost:1527/restaurant_Project";
    private Connection connection;
     PreparedStatement insertNewOrder;
     Scene scene;
     Stage stage;
    int Total;
    int int_random ;
    int Order_Number;
    @FXML
    ListView ListOfOrders;
    private  Spinner Nspinner;
    private LocalDate myObj = LocalDate.now();
    private Random rand = new Random();
    private String Date1 = myObj.toString();
    int ylable = 0;
    String TableN="1";

    /**
     * Initializes the controller class.
     */
 @FXML private javafx.scene.control.Button closeButton;
 
    SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10,1);
    static ObservableList<Integer>Total_Price=FXCollections.observableArrayList();
     ObservableList<String>DishsOrder=FXCollections.observableArrayList();
     ObservableList<String>Dish_price = FXCollections.observableArrayList();
    static ObservableList<Integer>Order_Id=FXCollections.observableArrayList();
    static ObservableList<Integer>NumberOfOreders=FXCollections.observableArrayList();
    private static ObservableList<Integer>Quantity=FXCollections.observableArrayList();
    
    @FXML
    private Pane Disheprice;
    @FXML
    private Pane NumberDish;
    @FXML
    private Text totalPrice;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Query();  
            // TODO 
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    public void Delete(ActionEvent event) {

        
        int index = ListOfOrders.getSelectionModel().getSelectedIndex();

        ListOfOrders.getItems().remove(index);
        Disheprice.getChildren().remove(index);

    
        Total_Price.remove(index);
        Integer Sum_Total_price = 0;
        for(int i=0;Total_Price.toArray().length>i;i++){
              Sum_Total_price = Sum_Total_price+Total_Price.get(i);
        }
        if(ListOfOrders.getItems().isEmpty()){
            Set_totalPrice(0+" $");
            Disheprice.getChildren();
            NumberDish.getChildren();
            ylable = 0;
            Total=0;
        }else{
            Set_totalPrice(Total_Price.get(0).toString()+" $");

            ylable= ylable -30;
        }
        NumberDish.getChildren().remove(index);
        Quantity.remove(index);
        if(ListOfOrders.getItems().toArray().length>=1){
            

        } 
        
        
        for(int i =0;Disheprice.getChildren().toArray().length>i;i++ ){
            int indexof=ListOfOrders.getItems().indexOf(ListOfOrders.getItems().get(i));
            if(index == ListOfOrders.getItems().toArray().length){
                break;
            }else{
                if(index<DishsOrder.indexOf(ListOfOrders.getItems().get(i))){
                Disheprice.getChildren().get(i).setLayoutY(NumberDish.getChildren().get(i).getLayoutY()-30);
                NumberDish.getChildren().get(i).setLayoutY(NumberDish.getChildren().get(i).getLayoutY()-30);
                }else{
                   
                }
            }

            
        }
        DishsOrder.remove(index);
        totalPrice.setText(Sum_Total_price.toString()+" $");
        

        
    }

    @FXML
    private void Submit(ActionEvent event) throws SQLException {
        while(NumberOfOreders.contains(Order_Number)){
            Order_Number = (int)Math.pow((rand.nextInt(50)),2);
        }
        NumberOfOreders.add(Order_Number);
        while(int_random==0 || Order_Id.contains(int_random)){
                int_random = (int)Math.pow((rand.nextInt(50)),2);
            }
        for(int i=0;Total_Price.toArray().length > i ;i++){
            Order_Id.add(int_random);
            
            if(chickId(int_random)==0)
              addOrder(int_random+i,Order_Number,ListOfOrders.getItems().get(i).toString(),Dish_price.get(i)+" $",Quantity.get(i),Date1,TableN);
        }

        ListOfOrders.getItems().clear();
        Dish_price.clear();
        Quantity.clear();
        Total_Price.clear();
                cancel(event);
        HomeController.i = 0;
        HomeController.dish_exists("Your Order will be ready in 15 min");
        
       int_random =0 ;

        
    }
        
    
    public Pane getDisheprice(){
        return this.Disheprice;
    }
    
    public Pane getNumberDish(){
        return this.NumberDish;
    }
    public void Query() throws SQLException{
            connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();
            String select = "SELECT * FROM ORDERS";

            insertNewOrder = connection.prepareStatement(         
            "INSERT INTO ORDERS" +                           
            "(ORDER_ID,ORDER_NUMBER,DISH_ORDER,ORDER_PRICE_PAR_DISH,QUANTITY,ORDER_DATE,Table_Number)" +     
            "VALUES (?,?,?,?,?,?,?)"); 

            String selec_sql = "SELECT count(*) FROM ORDERS";
            ResultSet result = statement.executeQuery(selec_sql);
            ResultSetMetaData metaData = result.getMetaData();
            ResultSet resultA = statement.executeQuery(select);
            int numberOfColumns = metaData.getColumnCount();            
            for (int i = 1; i <= numberOfColumns; i++) {
           	while(resultA.next()) { 
                    int id = resultA.getInt("ORDER_ID");int OrderNumber = resultA.getInt("Order_Number");
                    Order_Id.add(id);
                    NumberOfOreders.add(OrderNumber);
                }
            }
    }
    public void set_total_price(Integer item){
        Total_Price.add(item);

    }
    public void Set_totalPrice(String value){
        totalPrice.setText(value);
    }
    public void AddTotalPtrice(){
        boolean i =    ListOfOrders.getItems().isEmpty();


     if(!(i)){
  

         Total = Total+Total_Price.get(Total_Price.toArray().length-1);
         
         totalPrice.setText(String.valueOf(Total+" $"));
         
         
     }
    }
    public int chickId(int Number) throws SQLException{
        connection = DriverManager.getConnection(URL);
        Statement statement = connection.createStatement();
        String select = "SELECT Order_ID FROM ORDERS where Order_ID = "+Number;
      
        ResultSet result = statement.executeQuery(select); 
        return result.getRow();
    }
    public int addOrder(int id,int order_number,String Dish,String pice,int quantity,String ORDER_DATE,String Table_Number){
        try{
        insertNewOrder.setInt(1, id);
        insertNewOrder.setInt(2,order_number);
        insertNewOrder.setString(3, Dish);
        insertNewOrder.setString(4, pice);
        insertNewOrder.setInt(5,quantity);
        insertNewOrder.setString(6,ORDER_DATE);
        insertNewOrder.setString(7,Table_Number);
         return insertNewOrder.executeUpdate();
        }catch(SQLException sqlException) {

         return 0;
        }

    }
    public void setQuantity(int number){
        this.Quantity.add(number);
    }
    public void price_par_Dish(String price){
                Dish_price.add(price);
    }
    public static void cancel(ActionEvent event){
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


    
}
