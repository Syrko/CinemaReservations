CREATE TABLE "Cinema" (
	"cinemaID" VARCHAR(255) NOT NULL,
	"cinemaIs3D" BOOLEAN NOT NULL,
	"cinemaNumberOfSeats" integer NOT NULL,
	CONSTRAINT "Cinema_pk" PRIMARY KEY ("cinemaID")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Film" (
	"filmID" serial(255) NOT NULL,
	"filmTitle" serial(255) NOT NULL,
	"filmCategory" serial(255) NOT NULL,
	"filmDescription" serial(255) NOT NULL,
	CONSTRAINT "Film_pk" PRIMARY KEY ("filmID")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Provoli" (
	"provoliID" serial(255) NOT NULL,
	"provoliFilm" serial(255) NOT NULL,
	"provoliCinema" serial(255) NOT NULL,
	"provoliStartDate" serial NOT NULL,
	"provoliEndDate" serial NOT NULL,
	"provoliNumberOfReservations" serial NOT NULL,
	"provoliIsAvailable" serial NOT NULL,
	CONSTRAINT "Provoli_pk" PRIMARY KEY ("provoliID")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Admin" (
	"username" serial(255) NOT NULL,
	"name" BINARY NOT NULL,
	"password" serial(255) NOT NULL,
	CONSTRAINT "Admin_pk" PRIMARY KEY ("username")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Customer" (
	"name" BINARY NOT NULL,
	"username" serial(255) NOT NULL,
	"password" serial(255) NOT NULL,
	"reservation" VARCHAR(255) NOT NULL,
	CONSTRAINT "Customer_pk" PRIMARY KEY ("username")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "ContentAdmin" (
	"name" BINARY NOT NULL,
	"username" serial(255) NOT NULL,
	"password" serial(255) NOT NULL,
	CONSTRAINT "ContentAdmin_pk" PRIMARY KEY ("username")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Reservation" (
	"reservationID" serial(255) NOT NULL,
	"provoli" VARCHAR(255) NOT NULL,
	"numberOfSeats" integer NOT NULL,
	"reservationDate" DATE NOT NULL,
	CONSTRAINT "Reservation_pk" PRIMARY KEY ("reservationID")
) WITH (
  OIDS=FALSE
);





ALTER TABLE "Provoli" ADD CONSTRAINT "Provoli_fk0" FOREIGN KEY ("provoliFilm") REFERENCES "Film"("filmID");
ALTER TABLE "Provoli" ADD CONSTRAINT "Provoli_fk1" FOREIGN KEY ("provoliCinema") REFERENCES "Cinema"("cinemaID");




ALTER TABLE "Reservation" ADD CONSTRAINT "Reservation_fk0" FOREIGN KEY ("provoli") REFERENCES "Provoli"("provoliID");

