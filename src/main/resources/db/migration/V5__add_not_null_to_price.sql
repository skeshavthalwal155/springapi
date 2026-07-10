alter table order_items
    modify unit_price decimal (10, 2) not null;

alter table order_items
    modify total_price decimal (10, 2) not null;
