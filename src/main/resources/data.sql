INSERT INTO tripor.sido
SELECT * FROM triptest.sido;

INSERT INTO tripor.gugun
SELECT * FROM triptest.gugun;

INSERT INTO tripor.attraction_info (content_id, content_type_id, title, addr, tel, first_image, sido_code, gugun_code, latitude, longitude, mlevel, cat1, cat2, cat3, description)
SELECT 
    ai.content_id,
    ai.content_type_id,
    ai.title,
    CONCAT(ai.addr1, ' ', ai.addr2) AS addr,
    ai.tel,
    ai.first_image,
    ai.sido_code,
    ai.gugun_code,
    ai.latitude,
    ai.longitude,
    ai.mlevel,
    NULL AS cat1,
    NULL AS cat2,
    NULL AS cat3,
    ad.overview AS description
FROM 
    triptest.attraction_info ai
LEFT JOIN 
    triptest.attraction_description ad ON ai.content_id = ad.content_id;

use tripor;
select * from attraction_info;