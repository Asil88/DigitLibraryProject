

create sequence authors_id_seq start 1 increment 1;

create table public.authors (
                                id integer primary key not null default nextval('authors_id_seq'::regclass),
                                name character varying,
                                surname character varying,
                                biography character varying,
                                description character varying
);

create sequence articles_id_seq start 1 increment 1;

create table public.articles (
                                 id integer primary key not null default nextval('articles_id_seq'::regclass),
                                 title character varying,
                                 date_created timestamp without time zone,
                                 text character varying,
                                 author_id integer,
                                 foreign key (author_id) references public.authors (id)
                                     match simple on update cascade on delete cascade
);

create sequence books_id_seq start 1 increment 1;

create table public.books (
                              id integer primary key not null default nextval('books_id_seq'::regclass),
                              title character varying not null,
                              price double precision,
                              genre character varying,
                              series character varying,
                              annotation character varying,
                              availability character varying not null,
                              author_id integer,
                              file_name character varying,
                              foreign key (author_id) references public.authors (id)
                                  match simple on update cascade on delete cascade
);

create sequence users_id_seq start 1 increment 1;

create table public.users (
                              id integer primary key not null default nextval('users_id_seq'::regclass),
                              login character varying not null,
                              password character varying not null,
                              name character varying,
                              email character varying,
                              phone_number character varying
);
create unique index "User_login_key" on users using btree (login);
create unique index users_email_key on users using btree (email);
create unique index users_phone_number_key on users using btree (phone_number);

create sequence orders_id_seq start 1 increment 1;

create table public.orders (
                               id integer primary key not null default nextval('orders_id_seq'::regclass),
                               status character varying not null default 'EXPECTED',
                               date_created timestamp without time zone,
                               payment_method character varying not null default 'Naturoi',
                               user_id integer not null,
                               book_id integer,
                               foreign key (book_id) references public.books (id)
                                   match simple on update cascade on delete cascade,
                               foreign key (user_id) references public.users (id)
                                   match simple on update cascade on delete cascade
);

create sequence roles_id_seq start 1 increment 1;

create table public.roles (
                              id integer primary key not null default nextval('roles_id_seq'::regclass),
                              user_id integer not null,
                              role character varying default 'USER',
                              foreign key (user_id) references public.users (id)
                                  match simple on update cascade on delete cascade
);

create unique index roles_user_id_key on roles using btree (user_id);





