select A.medallion, concat((A.cnt1/B.cnt2*100),'%') as percentage_of_trips from (((select medallion, count(case when (pickup_longitude=0 and pickup_latitude=0 and dropoff_longitude=0 and dropoff_latitude=0) then 1 end) as cnt1 from trips group by medallion order by medallion) as A),
((select medallion, count(*) as cnt2 from trips group by medallion order by medallion) as B)) where A.medallion=B.medallion group by medallion order by medallion;;