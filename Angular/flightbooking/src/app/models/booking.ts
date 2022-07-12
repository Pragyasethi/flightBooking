import { PassengerDetails } from "./passenger-details";

export class Booking {
    bookingId!:number;
    flightId!:number;
    email!:String;
    phone!:number;
    departureDate!:Date;
    bookingDate!:Date;
    pnr!:string;
    passengerCount!:number;
    passengers!:PassengerDetails [];

}
