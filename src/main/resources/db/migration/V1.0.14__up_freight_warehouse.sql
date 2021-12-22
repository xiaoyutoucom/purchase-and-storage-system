ALTER TABLE public.freight_warehouse
    ADD COLUMN storeId  varchar(50) ;
delete from public.freight_warehouse;
INSERT INTO public.freight_warehouse(
    id, warehouse, code, shortcode, addressline, suburb, city, postcode, lon, lat, phonenumber,storeId)
VALUES (1, 'hamilton', 'HAM', 'A', '12 Sharpe Road', 'Rukuhia', 'Rukuhia', '3282', '175.342013565122', '-37.8511217708326', '+66496361111','3');
INSERT INTO public.freight_warehouse(
    id, warehouse, code, shortcode, addressline, suburb, city, postcode, lon, lat, phonenumber,storeId)
VALUES (2, 'otahuhu', 'OTA', 'C', '8 manu street', 'Otahuhu', 'Auckland', '2024', '174.830084278906', '-36.9475675795891', '+66496361111','1');
INSERT INTO public.freight_warehouse(
    id, warehouse, code, shortcode, addressline, suburb, city, postcode, lon, lat, phonenumber,storeId)
VALUES (3, 'christchurch', 'CHC', 'H', '75 Main South Road', 'Sockburn', 'Christchurch', '8042', '174.806866822152', '-43.5353218673018', '+6439749063','2');
INSERT INTO public.freight_warehouse(
    id, warehouse, code, shortcode, addressline, suburb, city, postcode, lon, lat, phonenumber,storeId)
VALUES (4, 'auckland', 'AKL', 'B', '306 Neilson Street', 'Onehunga', 'Auckland', '1061', '174.806866822152', '-36.9241426820229', '+66496361111','1');