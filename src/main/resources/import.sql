-- Insert Brand --
INSERT INTO brand (id, name, slug)
SELECT * FROM (
                  VALUES
                      (1, 'Toyota', LOWER('Toyota')),
                      (2, 'Honda', LOWER('Honda')),
                      (3, 'Ford', LOWER('Ford')),
                      (4, 'Chevrolet', LOWER('Chevrolet')),
                      (5, 'BMW', LOWER('BMW')),
                      (6, 'Mercedes', LOWER('Mercedes')),
                      (7, 'Audi', LOWER('Audi')),
                      (8, 'Tesla', LOWER('Tesla')),
                      (9, 'Nissan', LOWER('Nissan')),
                      (10, 'Hyundai', LOWER('Hyundai')),
                      (11, 'Kia', LOWER('Kia')),
                      (12, 'Porsche', LOWER('Porsche')),
                      (13, 'Jaguar', LOWER('Jaguar')),
                      (14, 'Lexus', LOWER('Lexus')),
                      (15, 'Land Rover', LOWER('Land Rover')),
                      (16, 'Fiat', LOWER('Fiat')),
                      (17, 'Mazda', LOWER('Mazda')),
                      (18, 'Subaru', LOWER('Subaru'))
              ) AS v(id, name, slug)
WHERE NOT EXISTS (
    SELECT 1 FROM brand WHERE name = v.name
);


-- Insert car type --
INSERT INTO cartype (name, slug)
SELECT name, LOWER(name) FROM (VALUES ('Sedan'), ('SUV'), ('Hatchback'), ('Convertible'), ('Coupe')
                              ) AS v(name)
WHERE NOT EXISTS (
    SELECT 1 FROM cartype WHERE name = v.name
);

-- Insert engine --
INSERT INTO engine (name, power, fueltype)
SELECT name, power, fueltype FROM (VALUES
                                       ('1.6L', '120hp', 'GASOLINE'),
                                       ('2.0L', '150hp', 'GASOLINE'),
                                       ('2.2L', '180hp', 'ETHANOL'),
                                       ('2.6L', '180hp', 'HYDROGENE'),
                                       ('3.0L', '250hp', 'ETHANOL'),
                                       ('2.1L', '200hp', 'ELECTRIC'),
                                       ('1.5L Hybrid', '130hp', 'HYBRID'),
                                       ('2.5L', '200hp', 'DIESEL'),
                                       ('80kW', '150hp', 'ELECTRIC'),
                                       ('3.3L', '280hp', 'HYDROGENE'),
                                       ('3.5L', '300hp', 'GASOLINE'),
                                       ('4.0L Turbo', '400hp', 'GASOLINE'),
                                       ('2.0L Turbo Hybrid', '220hp', 'HYBRID'),
                                       ('200kW', '270hp', 'ELECTRIC')
                                  ) AS proposed (name, power, fueltype)
WHERE NOT EXISTS (
    SELECT 1 FROM engine
    WHERE name = proposed.name AND power = proposed.power AND fueltype = proposed.fueltype
);

-- Insert color RVB et HEX --
INSERT INTO color (name, rvbref, hexref)
SELECT * FROM (VALUES ('Black', '0,0,0', '#000000'), ('White', '255,255,255', '#FFFFFF'),
                      ('Silver', '192,192,192', '#C0C0C0'), ('Gray', '128,128,128', '#808080'),
                      ('Red', '255,0,0', '#FF0000'), ('Blue', '0,0,255', '#0000FF'),
                      ('Brown', '165,42,42', '#A52A2A'), ('Green', '0,128,0', '#008000'),
                      ('Beige', '245,245,220', '#F5F5DC'), ('Yellow', '255,255,0', '#FFFF00'),
                      ('Gold', '255,215,0', '#FFD700'), ('Orange', '255,165,0', '#FFA500'),
                      ('Purple', '128,0,128', '#800080'), ('Pink', '255,192,203', '#FFC0CB'),
                      ('Maroon', '128,0,0', '#800000'), ('Lime Green', '50,205,50', '#32CD32'),
                      ('Cyan', '0,255,255', '#00FFFF'), ('Magenta', '255,0,255', '#FF00FF'),
                      ('Olive', '128,128,0', '#808000'), ('Navy', '0,0,128', '#000080'),
                      ('Teal', '0,128,128', '#008080'), ('Aqua', '0,255,255', '#00FFFF'),
                      ('Mint Green', '152,255,152', '#98FF98'), ('Coral', '255,127,80', '#FF7F50'),
                      ('Ivory', '255,255,240', '#FFFFF0'), ('Indigo', '75,0,130', '#4B0082'),
                      ('Charcoal', '54,69,79', '#36454F')
              ) AS v(name, rvb_ref, hex_ref)
WHERE NOT EXISTS (
    SELECT 1 FROM color WHERE name = v.name AND rvbref = v.rvb_ref AND hexref = v.hex_ref
);

-- Insert models depending on brand --
WITH ExpandedModels AS (
    SELECT b.id AS brand_id, unnest(b.models) AS model
    FROM (
             SELECT id, CASE
                            WHEN name = 'Toyota' THEN ARRAY['Corolla', 'Camry', 'RAV4', 'Highlander', 'Prius']
                            WHEN name = 'Honda' THEN ARRAY['Civic', 'Accord', 'CR-V', 'Pilot', 'Fit']
                            WHEN name = 'Ford' THEN ARRAY['Fiesta', 'Focus', 'Mustang', 'Explorer', 'F-150']
                            WHEN name = 'Chevrolet' THEN ARRAY['Malibu', 'Cruze', 'Silverado', 'Equinox', 'Camaro']
                            WHEN name = 'BMW' THEN ARRAY['3 Series', '5 Series', 'X5', 'X3', 'M3']
                            WHEN name = 'Mercedes' THEN ARRAY['C-Class', 'E-Class', 'S-Class', 'GLE', 'GLC']
                            WHEN name = 'Audi' THEN ARRAY['A3', 'A4', 'A6', 'Q5', 'Q7']
                            WHEN name = 'Tesla' THEN ARRAY['Model S', 'Model 3', 'Model X', 'Model Y']
                            WHEN name = 'Nissan' THEN ARRAY['Altima', 'Leaf', 'Maxima', 'Murano', 'Rogue']
                            WHEN name = 'Hyundai' THEN ARRAY['Sonata', 'Elantra', 'Tucson', 'Santa Fe', 'Kona']
                            WHEN name = 'Kia' THEN ARRAY['Optima', 'Sorento', 'Soul', 'Sportage', 'Rio']
                            WHEN name = 'Porsche' THEN ARRAY['911', 'Cayenne', 'Macan', 'Panamera', '718 Cayman']
                            WHEN name = 'Jaguar' THEN ARRAY['XE', 'XF', 'F-Type', 'E-Pace', 'F-Pace']
                            WHEN name = 'Lexus' THEN ARRAY['ES', 'IS', 'NX', 'RX', 'GX']
                            WHEN name = 'Land Rover' THEN ARRAY['Defender', 'Discovery', 'Range Rover', 'Range Rover Sport', 'Range Rover Evoque']
                            WHEN name = 'Fiat' THEN ARRAY['500', '500X', '500L', 'Panda', 'Tipo']
                            WHEN name = 'Mazda' THEN ARRAY['3', '6', 'CX-3', 'CX-5', 'MX-5 Miata']
                            WHEN name = 'Subaru' THEN ARRAY['Outback', 'Forester', 'Crosstrek', 'Impreza', 'Legacy']
                 END AS models
             FROM brand
         ) b
),

-- Insert engine into the model table --
     ValidEngines AS (SELECT model,
                             brand_id,
                             CASE
                                 WHEN model IN ('Model S', 'Model 3') THEN 14
                                 WHEN model IN ('Model X', 'Model Y', 'Murano', 'Tucson', 'Rogue', 'E-Pace', 'GX') THEN 9
                                 WHEN model IN ('Corolla', 'Civic', 'Mustang', '911', '3 Series') THEN 1
                                 WHEN model IN ('Camry', 'Accord', 'F-150', 'Cayenne', 'E-Class') THEN 2
                                 WHEN model IN ('RAV4', 'CR-V', 'Silverado', 'Macan', 'A4') THEN 3
                                 WHEN model IN ('Highlander', 'Pilot', 'Equinox', 'Panamera', '5 Series') THEN 4
                                 WHEN model IN
                                      ('Prius', 'Fit', 'Malibu', 'Q5', 'X5', 'Outback', 'Forester', 'Crosstrek', 'Impreza',
                                       'Legacy') THEN 5
                                 WHEN model IN ('Explorer', 'Rio', 'Cruze', 'XE', 'IS') THEN 6
                                 WHEN model IN ('Focus', 'Optima', 'Leaf', 'XF', 'NX') THEN 7
                                 WHEN model IN ('Fiesta', 'Sorento', 'Maxima', 'F-Type', 'RX') THEN 8
                                 WHEN model IN ('Santa Fe', 'Sportage', 'Altima', 'F-Pace', 'Defender') THEN 10
                                 WHEN model IN ('Kona', '718 Cayman', 'Discovery', 'Range Rover', '500') THEN 11
                                 WHEN model IN ('Elantra', '500X', '500L', 'Panda', 'Tipo') THEN 12
                                 WHEN model IN ('3', '6', 'CX-3', 'CX-5', 'MX-5 Miata') THEN 13
                                 ELSE 1
                                 END AS engine_id
                      FROM ExpandedModels)
INSERT INTO model (name, brand_id, engine_id)
SELECT model, brand_id, engine_id
FROM ValidEngines
WHERE NOT EXISTS (
    SELECT 1 FROM model WHERE name = ValidEngines.model AND brand_id = ValidEngines.brand_id
);

-- Insert transmissions --
INSERT INTO car_transmissions (car_id, transmission)
SELECT m.id,
       CASE
           WHEN t.rn = 1 THEN 'MANUAL'
           WHEN t.rn = 2 THEN 'TORQUE'
           WHEN t.rn = 3 THEN 'SEMI_AUTOMATIC'
           WHEN t.rn = 4 THEN 'DUAL_CLUTCH'
           WHEN t.rn = 5 THEN 'TRIPTONIC'
           WHEN t.rn = 6 THEN 'CVT'
           END AS transmission
FROM model m,
     LATERAL (SELECT generate_series(1, 3) AS rn) t;

-- Insert 2-3 random colors to each model --
INSERT INTO model_color (model_id, color_id)
SELECT m.id, c.id
FROM model m,
     LATERAL (SELECT id FROM color ORDER BY random() LIMIT 3) c;

-- Insert car types (Tesla models are always Sedan) --
INSERT INTO model_cartype (model_id, cartype_id)
SELECT m.id,
       CASE
           WHEN m.name IN ('Model S', 'Model 3', 'Model X', 'Model Y') THEN 1
           ELSE (random() * 4 + 1)::int
           END AS cartype_id
FROM model m;

-- Reset the sequence, starts counting from the next available integer after the highest ID inserted --
SELECT setval('Brand_SEQ', COALESCE((SELECT MAX(id)+1 FROM brand), 1), false);
