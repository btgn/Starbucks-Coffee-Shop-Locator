
CREATE TABLE starbucks(
brand varchar(32)  ,
  storenumber varchar(32)  ,
  Name1 varchar(50)  ,
  Ownership_Type varchar(32)  ,
  Facility_ID varchar(100)  ,
  Features_Products varchar(200)  ,
  Features_Service varchar(200)  ,
  Features_Stations varchar(200)  ,
  FoodRegion varchar(32)  ,
  Venue_Type varchar(32)  ,
  Phone_Number varchar(15)  ,
  location varchar(200)  ,
  Street_Address varchar(100)  ,
  StreetLine1 varchar(100)  ,
  StreetLine2 varchar(100)  ,
  city varchar(32)  ,
  State varchar(5)  ,
  zip varchar(15)  ,
  Country varchar(10)  ,
  Coordinates varchar(60)  ,
  latitude varchar(32)  ,
  longitude varchar(32)  ,
  InsertDate varchar(32)  
);

select city,location,zip (3959 * acos(cos(radians(latitude)) * cos(radians("+lat+")) * cos(radians(longitude) - radians("+lon+")) + sin(radians("+lat+")) * sin(radians(latitude)))) AS distance FROM starbucks HAVING distance < " +radius+ " ";

select city,location,zip from starbucks where city like city;

select city,location,zip from starbucks where zip like zip;