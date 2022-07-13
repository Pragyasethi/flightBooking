import { PassengerDetails } from "./passenger-details";

export class Booking {
    id!:number;
    flightId!:number;
    email!:String;
    phone!:number;
    departureDate!:Date;
    bookingDate!:Date;
    pnr!:string;
    noOfSeats!:number;
    passengerDetails!:PassengerDetails [];
    status!:string;
    flightNumber!: string;
    fromCity!:string;
    toCity!:string;


}
