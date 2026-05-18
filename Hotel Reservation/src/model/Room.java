package model;

public class Room implements IRoom {
    private final String roomNumber;
    private final Double price;
    private final RoomType roomType;

    public Room(String roomNumber,Double price, RoomType roomType){
        this.roomNumber=roomNumber;
        this.price=price;
        this.roomType=roomType;
    }

    private void validateRoomNumber(String roomNumber){
        if(roomNumber == null || roomNumber.trim().isEmpty()){
            throw new IllegalArgumentException("Room number cannot be null or empty");
        }
    }

    private void validatePrice(Double price){
        if(price == null || price<=0){
            throw new IllegalArgumentException("Room price must be positive");
        }
    }

    @Override
    public  String getRoomNumber(){
        return roomNumber;
    }

    public  RoomType getRoomType(){
        return roomType;
    }

    public Double getRoomPrice(){
        return price;
    }

    @Override
    public boolean isFree() {
        return false;
    }
    @Override
    public String toString() {
        return "Room Number= "+ roomNumber+" , Price= "+price+ " ,Type= "+roomType;

    }
}

