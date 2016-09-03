select M.medallion_type, count(*) as total_trips, sum(F.fare_amount) as total_revenue, concat((avg(F.tip_amount/F.fare_amount)*100),'%') as avg_tip_percentage from fares F, medallions M where F.medallion= M.medallion group by M.medallion_type order by M.medallion_type;