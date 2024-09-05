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
INSERT INTO cartype (id, name, slug)
SELECT id, name, LOWER(name)
FROM (VALUES
          (1,'Sedan'),
          (2,'SUV'),
          (3,'Hatchback'),
          (4,'Convertible'),
          (5,'Coupe')
      ) AS v(id, name)
WHERE NOT EXISTS (
    SELECT 1 FROM cartype WHERE name = v.name
);

-- Insert engine --
INSERT INTO engine (id, name, power, fueltype)
SELECT id, name, power, fueltype
FROM (VALUES
          (1, '1.6L', '120hp', 'GASOLINE'),
          (2, '2.0L', '150hp', 'GASOLINE'),
          (3, '2.2L', '180hp', 'ETHANOL'),
          (4, '2.6L', '180hp', 'HYDROGENE'),
          (5,'3.0L', '250hp', 'ETHANOL'),
          (6,'2.1L', '200hp', 'ELECTRIC'),
          (7,'1.5L Hybrid', '130hp', 'HYBRID'),
          (8,'2.5L', '200hp', 'DIESEL'),
          (9,'80kW', '150hp', 'ELECTRIC'),
          (10,'3.3L', '280hp', 'HYDROGENE'),
          (11,'3.5L', '300hp', 'GASOLINE'),
          (12,'4.0L Turbo', '400hp', 'GASOLINE'),
          (13,'2.0L Turbo Hybrid', '220hp', 'HYBRID'),
          (14,'200kW', '270hp', 'ELECTRIC')
     ) AS v(id, name, power, fueltype)
WHERE NOT EXISTS (
    SELECT 1 FROM engine
    WHERE name =v.name AND power = v.power AND fueltype = v.fueltype
);

-- Insert color RVB et HEX --
INSERT INTO color (id, name, rvbref, hexref)
SELECT id, name, rvb_ref, hex_ref
FROM (VALUES
          (1, 'Black', '0,0,0', '#000000'),
          (2, 'White', '255,255,255', '#FFFFFF'),
          (3, 'Silver', '192,192,192', '#C0C0C0'),
          (4, 'Gray', '128,128,128', '#808080'),
          (5, 'Red', '255,0,0', '#FF0000'),
          (6, 'Blue', '0,0,255', '#0000FF'),
          (7, 'Brown', '165,42,42', '#A52A2A'),
          (8, 'Green', '0,128,0', '#008000'),
          (9, 'Beige', '245,245,220', '#F5F5DC'),
          (10, 'Yellow', '255,255,0', '#FFFF00'),
          (11, 'Gold', '255,215,0', '#FFD700'),
          (12, 'Orange', '255,165,0', '#FFA500'),
          (13, 'Purple', '128,0,128', '#800080'),
          (14, 'Pink', '255,192,203', '#FFC0CB'),
          (15, 'Maroon', '128,0,0', '#800000'),
          (16, 'Lime Green', '50,205,50', '#32CD32'),
          (17, 'Cyan', '0,255,255', '#00FFFF'),
          (18, 'Magenta', '255,0,255', '#FF00FF'),
          (19, 'Olive', '128,128,0', '#808000'),
          (20, 'Navy', '0,0,128', '#000080'),
          (21, 'Teal', '0,128,128', '#008080'),
          (22, 'Aqua', '0,255,255', '#00FFFF'),
          (23, 'Mint Green', '152,255,152', '#98FF98'),
          (24, 'Coral', '255,127,80', '#FF7F50'),
          (25, 'Ivory', '255,255,240', '#FFFFF0'),
          (26, 'Indigo', '75,0,130', '#4B0082'),
          (27, 'Charcoal', '54,69,79', '#36454F')
     ) AS v(id, name, rvb_ref, hex_ref)
WHERE NOT EXISTS (
    SELECT 1 FROM color WHERE name = v.name AND rvbref = v.rvb_ref AND hexref = v.hex_ref
);

-- Create a sequence for engine table if not exists
CREATE SEQUENCE IF NOT EXISTS model_id_seq;
ALTER TABLE model ALTER COLUMN id SET DEFAULT nextval('model_id_seq');
-- ExpandedModels: Assign models and IDs to each brand
WITH ExpandedModels AS (
    -- Toyota models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('Corolla'),
                       ('Camry'),
                       ('RAV4'),
                       ('Highlander'),
                       ('Prius')
    ) AS m(model_name)
    WHERE b.name = 'Toyota'

    UNION ALL

    -- Honda models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('Civic'),
                       ('Accord'),
                       ('CR-V'),
                       ('Pilot'),
                       ('Fit')
    ) AS m(model_name)
    WHERE b.name = 'Honda'

    UNION ALL

    -- Ford models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('Fiesta'),
                       ('Focus'),
                       ('Mustang'),
                       ('Explorer'),
                       ('F-150')
    ) AS m(model_name)
    WHERE b.name = 'Ford'

    UNION ALL

    -- Chevrolet models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('Malibu'),
                       ('Cruze'),
                       ('Silverado'),
                       ('Equinox'),
                       ('Camaro')
    ) AS m(model_name)
    WHERE b.name = 'Chevrolet'

    UNION ALL

    -- BMW models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('3 Series'),
                       ('5 Series'),
                       ('X5'),
                       ('X3'),
                       ('M3')
    ) AS m(model_name)
    WHERE b.name = 'BMW'

    UNION ALL

    -- Mercedes models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('C-Class'),
                       ('E-Class'),
                       ('S-Class'),
                       ('GLE'),
                       ('GLC')
    ) AS m(model_name)
    WHERE b.name = 'Mercedes'

    UNION ALL

    -- Audi models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('A3'),
                       ('A4'),
                       ('A6'),
                       ('Q5'),
                       ('Q7')
    ) AS m(model_name)
    WHERE b.name = 'Audi'

    UNION ALL

    -- Tesla models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('Model S'),
                       ('Model 3'),
                       ('Model X'),
                       ('Model Y')
    ) AS m(model_name)
    WHERE b.name = 'Tesla'

    UNION ALL

    -- Nissan models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('Altima'),
                       ('Leaf'),
                       ('Maxima'),
                       ('Murano'),
                       ('Rogue')
    ) AS m(model_name)
    WHERE b.name = 'Nissan'

    UNION ALL

    -- Hyundai models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('Sonata'),
                       ('Elantra'),
                       ('Tucson'),
                       ('Santa Fe'),
                       ('Kona')
    ) AS m(model_name)
    WHERE b.name = 'Hyundai'

    UNION ALL

    -- Kia models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('Optima'),
                       ('Sorento'),
                       ('Soul'),
                       ('Sportage'),
                       ('Rio')
    ) AS m(model_name)
    WHERE b.name = 'Kia'

    UNION ALL

    -- Porsche models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('911'),
                       ('Cayenne'),
                       ('Macan'),
                       ('Panamera'),
                       ('718 Cayman')
    ) AS m(model_name)
    WHERE b.name = 'Porsche'

    UNION ALL

    -- Jaguar models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('XE'),
                       ('XF'),
                       ('F-Type'),
                       ('E-Pace'),
                       ('F-Pace')
    ) AS m(model_name)
    WHERE b.name = 'Jaguar'

    UNION ALL

    -- Lexus models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('ES'),
                       ('IS'),
                       ('NX'),
                       ('RX'),
                       ('GX')
    ) AS m(model_name)
    WHERE b.name = 'Lexus'

    UNION ALL

    -- Land Rover models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('Defender'),
                       ('Discovery'),
                       ('Range Rover'),
                       ('Range Rover Sport'),
                       ('Range Rover Evoque')
    ) AS m(model_name)
    WHERE b.name = 'Land Rover'

    UNION ALL

    -- Fiat models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('500'),
                       ('500X'),
                       ('500L'),
                       ('Panda'),
                       ('Tipo')
    ) AS m(model_name)
    WHERE b.name = 'Fiat'

    UNION ALL

    -- Mazda models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('3'),
                       ('6'),
                       ('CX-3'),
                       ('CX-5'),
                       ('MX-5 Miata')
    ) AS m(model_name)
    WHERE b.name = 'Mazda'

    UNION ALL

    -- Subaru models
    SELECT b.id AS brand_id, m.model_name
    FROM brand b, (VALUES
                       ('Outback'),
                       ('Forester'),
                       ('Crosstrek'),
                       ('Impreza'),
                       ('Legacy')
    ) AS m(model_name)
    WHERE b.name = 'Subaru'
),

-- Insert engine into the model table --
     ValidEngines AS (
         SELECT model_name AS model,
                brand_id::BIGINT,
                CASE
                    WHEN  model_name IN ('Model S', 'Model 3') THEN 14
                    WHEN  model_name IN ('Model X', 'Model Y', 'Murano', 'Tucson', 'Rogue', 'E-Pace', 'GX') THEN 9
                    WHEN  model_name IN ('Corolla', 'Civic', 'Mustang', '911', '3 Series') THEN 1
                    WHEN  model_name IN ('Camry', 'Accord', 'F-150', 'Cayenne', 'E-Class') THEN 2
                    WHEN  model_name IN ('RAV4', 'CR-V', 'Silverado', 'Macan', 'A4') THEN 3
                    WHEN  model_name IN ('Highlander', 'Pilot', 'Equinox', 'Panamera', '5 Series') THEN 4
                    WHEN  model_name IN ('Prius', 'Fit', 'Malibu', 'Q5', 'X5', 'Outback', 'Forester', 'Crosstrek', 'Impreza', 'Legacy') THEN 5
                    WHEN  model_name IN ('Explorer', 'Rio', 'Cruze', 'XE', 'IS') THEN 6
                    WHEN  model_name IN ('Focus', 'Optima', 'Leaf', 'XF', 'NX') THEN 7
                    WHEN  model_name IN ('Fiesta', 'Sorento', 'Maxima', 'F-Type', 'RX') THEN 8
                    WHEN  model_name IN ('Santa Fe', 'Sportage', 'Altima', 'F-Pace', 'Defender') THEN 10
                    WHEN  model_name IN ('Kona', '718 Cayman', 'Discovery', 'Range Rover', '500') THEN 11
                    WHEN  model_name IN ('Elantra', '500X', '500L', 'Panda', 'Tipo') THEN 12
                    WHEN  model_name IN ('3', '6', 'CX-3', 'CX-5', 'MX-5 Miata') THEN 13
                    ELSE 1
                  END::BIGINT AS engine_id
         FROM ExpandedModels
     )

-- Insert models into the table with slug
INSERT INTO model (name, brand_id, engine_id, slug)
SELECT model,
       brand_id,
       engine_id,
       LOWER(REPLACE(model, ' ', '-')) AS slug -- Generate slug by replacing spaces with hyphens and making it lowercase
FROM ValidEngines
RETURNING id, name, brand_id, engine_id, slug;


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
SELECT setval('brand_SEQ', COALESCE((SELECT MAX(id)+1 FROM brand), 1), false);
SELECT setval('cartype_SEQ', COALESCE((SELECT MAX(id)+1 FROM cartype), 1), false);
SELECT setval('brand_SEQ', COALESCE((SELECT MAX(id)+1 FROM brand), 1), false);
SELECT setval('color_SEQ', COALESCE((SELECT MAX(id)+1 FROM color), 1), false);
SELECT setval('engine_SEQ', COALESCE((SELECT MAX(id)+1 FROM engine), 1), false);
