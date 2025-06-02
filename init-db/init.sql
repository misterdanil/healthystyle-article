--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4 (Ubuntu 16.4-0ubuntu0.24.04.2)
-- Dumped by pg_dump version 16.4 (Ubuntu 16.4-0ubuntu0.24.04.2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: _order; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public._order (
    _order integer NOT NULL,
    created_on timestamp(6) with time zone NOT NULL,
    fragment_id bigint NOT NULL,
    id bigint NOT NULL,
    dtype character varying(31) NOT NULL
);


ALTER TABLE public._order OWNER TO daniel;

--
-- Name: article; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.article (
    category_id bigint NOT NULL,
    created_on timestamp(6) with time zone NOT NULL,
    id bigint NOT NULL,
    image_id bigint,
    user_id bigint NOT NULL,
    title character varying(255) NOT NULL
);


ALTER TABLE public.article OWNER TO daniel;

--
-- Name: article_link; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.article_link (
    article_id bigint NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE public.article_link OWNER TO daniel;

--
-- Name: article_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.article_seq
    START WITH 1
    INCREMENT BY 5
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.article_seq OWNER TO daniel;

--
-- Name: article_source; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.article_source (
    _order integer NOT NULL,
    article_id bigint,
    created_on timestamp(6) with time zone NOT NULL,
    id bigint NOT NULL,
    source_id bigint NOT NULL
);


ALTER TABLE public.article_source OWNER TO daniel;

--
-- Name: article_source_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.article_source_seq
    START WITH 1
    INCREMENT BY 20
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.article_source_seq OWNER TO daniel;

--
-- Name: bold_part; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.bold_part (
    id bigint NOT NULL
);


ALTER TABLE public.bold_part OWNER TO daniel;

--
-- Name: category; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.category (
    _order integer NOT NULL,
    created_on timestamp(6) with time zone,
    id bigint NOT NULL,
    parent_category_id bigint,
    title character varying(255) NOT NULL
);


ALTER TABLE public.category OWNER TO daniel;

--
-- Name: category_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.category_seq
    START WITH 1
    INCREMENT BY 5
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.category_seq OWNER TO daniel;

--
-- Name: comment; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.comment (
    article_id bigint NOT NULL,
    comment_id bigint,
    created_on timestamp(6) with time zone NOT NULL,
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    text character varying(255) NOT NULL
);


ALTER TABLE public.comment OWNER TO daniel;

--
-- Name: comment_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.comment_seq
    START WITH 1
    INCREMENT BY 5
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.comment_seq OWNER TO daniel;

--
-- Name: cursive_part; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.cursive_part (
    id bigint NOT NULL
);


ALTER TABLE public.cursive_part OWNER TO daniel;

--
-- Name: fragment; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.fragment (
    _order integer NOT NULL,
    article_id bigint NOT NULL,
    created_on timestamp(6) with time zone NOT NULL,
    id bigint NOT NULL,
    title character varying(300)
);


ALTER TABLE public.fragment OWNER TO daniel;

--
-- Name: fragment_image; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.fragment_image (
    id bigint NOT NULL,
    image_id bigint NOT NULL
);


ALTER TABLE public.fragment_image OWNER TO daniel;

--
-- Name: fragment_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.fragment_seq
    START WITH 1
    INCREMENT BY 20
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.fragment_seq OWNER TO daniel;

--
-- Name: image; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.image (
    created_on timestamp(6) with time zone NOT NULL,
    id bigint NOT NULL,
    image_id bigint,
    source_id bigint
);


ALTER TABLE public.image OWNER TO daniel;

--
-- Name: image_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.image_seq
    START WITH 1
    INCREMENT BY 20
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.image_seq OWNER TO daniel;

--
-- Name: link_part; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.link_part (
    id bigint NOT NULL,
    source character varying(255) NOT NULL
);


ALTER TABLE public.link_part OWNER TO daniel;

--
-- Name: mark; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.mark (
    value integer NOT NULL,
    created_on timestamp(6) with time zone NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE public.mark OWNER TO daniel;

--
-- Name: mark_article; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.mark_article (
    article_id bigint NOT NULL,
    created_on timestamp(6) with time zone NOT NULL,
    id bigint NOT NULL,
    mark_id bigint,
    user_id bigint NOT NULL
);


ALTER TABLE public.mark_article OWNER TO daniel;

--
-- Name: mark_article_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.mark_article_seq
    START WITH 1
    INCREMENT BY 5
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.mark_article_seq OWNER TO daniel;

--
-- Name: mark_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.mark_seq
    START WITH 1
    INCREMENT BY 5
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.mark_seq OWNER TO daniel;

--
-- Name: order_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.order_seq
    START WITH 1
    INCREMENT BY 20
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.order_seq OWNER TO daniel;

--
-- Name: quote; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.quote (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    text character varying(255) NOT NULL
);


ALTER TABLE public.quote OWNER TO daniel;

--
-- Name: roll; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.roll (
    id bigint NOT NULL
);


ALTER TABLE public.roll OWNER TO daniel;

--
-- Name: roll_element; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.roll_element (
    _order integer NOT NULL,
    created_on timestamp(6) with time zone NOT NULL,
    id bigint NOT NULL,
    roll_id bigint NOT NULL,
    text character varying(500) NOT NULL
);


ALTER TABLE public.roll_element OWNER TO daniel;

--
-- Name: roll_element_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.roll_element_seq
    START WITH 1
    INCREMENT BY 20
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roll_element_seq OWNER TO daniel;

--
-- Name: source; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.source (
    id bigint NOT NULL,
    description character varying(255) NOT NULL,
    link character varying(255) NOT NULL
);


ALTER TABLE public.source OWNER TO daniel;

--
-- Name: source_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.source_seq
    START WITH 1
    INCREMENT BY 20
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.source_seq OWNER TO daniel;

--
-- Name: text; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.text (
    id bigint NOT NULL
);


ALTER TABLE public.text OWNER TO daniel;

--
-- Name: text_part; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.text_part (
    _order integer NOT NULL,
    created_on timestamp(6) with time zone NOT NULL,
    id bigint NOT NULL,
    text_id bigint NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.text_part OWNER TO daniel;

--
-- Name: text_part_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.text_part_seq
    START WITH 1
    INCREMENT BY 20
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.text_part_seq OWNER TO daniel;

--
-- Name: view; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.view (
    article_id bigint NOT NULL,
    created_on timestamp(6) with time zone NOT NULL,
    id bigint NOT NULL,
    user_id bigint,
    ip character varying(255)
);


ALTER TABLE public.view OWNER TO daniel;

--
-- Name: view_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.view_seq
    START WITH 1
    INCREMENT BY 5
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.view_seq OWNER TO daniel;

--
-- Data for Name: _order; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public._order (_order, created_on, fragment_id, id, dtype) FROM stdin;
1	2025-05-06 22:13:05.739009+03	22	1	text
1	2025-05-06 22:16:41.268627+03	23	2	text
1	2025-05-06 22:35:12.391288+03	44	22	text
2	2025-05-06 22:35:12.688713+03	44	23	image
1	2025-05-06 22:51:02.493078+03	45	24	text
2	2025-05-06 22:51:02.779001+03	45	25	image
1	2025-05-06 22:54:43.351276+03	62	42	text
2	2025-05-06 22:54:43.571877+03	62	43	image
1	2025-05-06 22:54:43.622651+03	63	44	roll
1	2025-05-06 23:00:12.317989+03	64	45	text
2	2025-05-06 23:00:12.671731+03	64	46	image
1	2025-05-06 23:00:12.796799+03	65	47	roll
1	2025-05-06 23:17:57.341588+03	122	82	quote
1	2025-05-07 23:39:55.654523+03	142	102	text
2	2025-05-07 23:39:56.253441+03	142	103	quote
1	2025-05-07 23:39:56.295074+03	143	104	roll
2	2025-05-07 23:39:56.545296+03	143	105	image
1	2025-05-07 23:39:56.634356+03	144	106	link
2	2025-05-07 23:39:56.671938+03	144	107	text
\.


--
-- Data for Name: article; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.article (category_id, created_on, id, image_id, user_id, title) FROM stdin;
1	2025-05-06 22:06:42.487596+03	1	1	1	выфвцфвцфвфцвф
1	2025-05-06 22:13:05.484425+03	7	22	1	выфвцфвцфвфцвф
1	2025-05-06 22:16:40.992592+03	8	23	1	выфвцфвцфвфцвф
1	2025-05-06 22:19:27.120367+03	9	24	1	выфвцфвцфвфцвф
1	2025-05-06 22:30:55.452014+03	12	42	1	выфвцфвцфвфцвф
1	2025-05-06 22:31:44.440186+03	13	43	1	выфвцфвцфвфцвф
1	2025-05-06 22:35:12.35107+03	14	44	1	dfgdfgdfg fdgfdgdf gfd gfdgdfgdfgdfg
1	2025-05-06 22:51:02.246798+03	15	46	1	dwadwadwadwd awdhan dahwdn dnadnawnjanfdsfd
1	2025-05-06 22:54:43.123896+03	17	62	1	dwadwadwadwd awdhan dahwdn dnadnawnjanfdsfd
1	2025-05-06 23:00:12.288995+03	18	64	1	dwadwadwadwd awdhan dahwdn dnadnawnjanfdsfd
1	2025-05-06 23:17:57.1275+03	32	122	1	dawdsfdscdssfesf
3	2025-05-07 23:39:55.185076+03	37	142	1	10 шагов к здоровому образу жизни
\.


--
-- Data for Name: article_link; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.article_link (article_id, id) FROM stdin;
1	106
\.


--
-- Data for Name: article_source; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.article_source (_order, article_id, created_on, id, source_id) FROM stdin;
1	14	2025-05-06 22:35:12.723504+03	1	45
1	32	2025-05-06 23:17:57.35923+03	2	123
1	37	2025-05-07 23:39:56.699359+03	22	143
\.


--
-- Data for Name: bold_part; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.bold_part (id) FROM stdin;
4
9
25
31
63
\.


--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.category (_order, created_on, id, parent_category_id, title) FROM stdin;
1	2025-05-06 21:29:33.39135+03	1	\N	Спорт
2	2025-05-06 21:29:33.391357+03	2	\N	Еда
3	2025-05-06 21:29:33.391359+03	3	\N	Здоровье
\.


--
-- Data for Name: comment; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.comment (article_id, comment_id, created_on, id, user_id, text) FROM stdin;
1	\N	2025-05-09 18:49:38.023353+03	2	1	Ну такое себе, могло быть и лучше
1	\N	2025-05-09 19:04:11.521773+03	3	1	Что за дела? Обоснуй
1	2	2025-05-09 19:07:35.216311+03	7	1	УЖАС ПРОСТО
1	2	2025-05-09 19:08:32.07206+03	12	1	Что за дела? Обоснуй
37	\N	2025-05-23 14:59:49.350062+03	17	1	Неубедительно
\.


--
-- Data for Name: cursive_part; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.cursive_part (id) FROM stdin;
2
7
23
29
65
\.


--
-- Data for Name: fragment; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.fragment (_order, article_id, created_on, id, title) FROM stdin;
1	1	2025-05-06 22:06:42.633883+03	1	выфвцфвцфвфцвф
1	7	2025-05-06 22:13:05.709249+03	22	выфвцфвцфвфцвф
1	8	2025-05-06 22:16:41.234741+03	23	выфвцфвцфвфцвф
1	9	2025-05-06 22:19:27.154353+03	24	выфвцфвцфвфцвф
1	12	2025-05-06 22:30:55.918102+03	42	выфвцфвцфвфцвф
1	13	2025-05-06 22:31:44.469089+03	43	выфвцфвцфвфцвф
1	14	2025-05-06 22:35:12.377762+03	44	dfgdfgdfg fdgfdgdf gfd gfdgdfgdfgdfg
1	15	2025-05-06 22:51:02.450853+03	45	fsfsdffsdf sdfsdfsdfsdfsdf sfdsfsdfe
1	17	2025-05-06 22:54:43.323174+03	62	fsfsdffsdf sdfsdfsdfsdfsdf sfdsfsdfe
2	17	2025-05-06 22:54:43.603358+03	63	dwadwadwadwd awdhan dahwdn dnadnawnjanfdsfd
1	18	2025-05-06 23:00:12.31005+03	64	fsfsdffsdf sdfsdfsdfsdfsdf sfdsfsdfe
2	18	2025-05-06 23:00:12.778441+03	65	dwadwadwadwd awdhan dahwdn dnadnawnjanfdsfd
1	32	2025-05-06 23:17:57.31005+03	122	dawdsfdscdssfesf
1	37	2025-05-07 23:39:55.611955+03	142	Введение
2	37	2025-05-07 23:39:56.284573+03	143	Полезные привычки
3	37	2025-05-07 23:39:56.590654+03	144	Дополнительные возможности
\.


--
-- Data for Name: fragment_image; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.fragment_image (id, image_id) FROM stdin;
23	45
25	47
43	63
46	65
105	143
\.


--
-- Data for Name: image; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.image (created_on, id, image_id, source_id) FROM stdin;
2025-05-06 22:13:05.485876+03	22	1	22
2025-05-06 22:16:40.999988+03	23	2	23
2025-05-06 22:19:27.12208+03	24	\N	24
2025-05-06 22:30:55.455657+03	42	1	42
2025-05-06 22:31:44.441612+03	43	2	43
2025-05-06 22:35:12.35242+03	44	3	44
2025-05-06 22:35:12.666996+03	45	4	\N
2025-05-06 22:51:02.257874+03	46	5	46
2025-05-06 22:51:02.776541+03	47	6	\N
2025-05-06 22:54:43.124864+03	62	1	62
2025-05-06 22:54:43.565971+03	63	2	\N
2025-05-06 23:00:12.289793+03	64	\N	63
2025-05-06 23:00:12.65557+03	65	\N	\N
2025-05-06 23:17:57.128742+03	122	8	122
2025-05-07 23:39:55.196442+03	142	1	142
2025-05-07 23:39:56.516995+03	143	2	\N
2025-05-06 22:06:42.488633+03	1	60	1
\.


--
-- Data for Name: link_part; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.link_part (id, source) FROM stdin;
5	https://lifehacker.ru/special/sport/
11	https://lifehacker.ru/special/sport/
27	https://lifehacker.ru/special/sport/
33	https://lifehacker.ru/special/sport/
67	https://gnicpm.ru/articles/zdorovyj-obraz-zhizni/chto-takoe-nizkaya-umerennaya-i-intensivnaya-fizicheskaya-aktivnost.html
\.


--
-- Data for Name: mark; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.mark (value, created_on, id) FROM stdin;
3	2025-05-08 00:44:45.066024+03	1
1	2025-05-08 00:51:43.294672+03	7
2	2025-05-08 00:51:43.385878+03	8
4	2025-05-08 00:53:22.080802+03	12
5	2025-05-08 00:53:22.188027+03	13
\.


--
-- Data for Name: mark_article; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.mark_article (article_id, created_on, id, mark_id, user_id) FROM stdin;
37	2025-05-08 00:54:38.94058+03	2	1	1
37	2025-05-08 00:54:47.912843+03	3	12	1
1	2025-05-09 18:42:52.416478+03	7	7	1
1	2025-05-09 18:47:55.374481+03	12	8	1
\.


--
-- Data for Name: quote; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.quote (id, name, text) FROM stdin;
82	dwadwad	dwadadsfsdfsdf
103	Гиппократ	Если мы могли бы дать каждому человеку нужное количество пищи и физической активности, ни в избытке, ни в недостатке, мы бы нашли путь к здоровью.
\.


--
-- Data for Name: roll; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.roll (id) FROM stdin;
44
47
104
\.


--
-- Data for Name: roll_element; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.roll_element (_order, created_on, id, roll_id, text) FROM stdin;
1	2025-05-06 22:54:43.63855+03	1	44	Отжимания
2	2025-05-06 22:54:43.67402+03	2	44	Приседания бред полный
3	2025-05-06 22:54:43.69004+03	3	44	НУ что-то ещё
1	2025-05-06 23:00:12.81162+03	4	47	Отжимания
2	2025-05-06 23:00:12.875853+03	5	47	Приседания бред полный
3	2025-05-06 23:00:12.891471+03	6	47	НУ что-то ещё
1	2025-05-07 23:39:56.337283+03	42	104	Пейте достаточно воды
2	2025-05-07 23:39:56.449201+03	43	104	Высыпайтесь не менее 7 часов в день
3	2025-05-07 23:39:56.482151+03	44	104	Минимизируйте стресс с помощью медитации
\.


--
-- Data for Name: source; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.source (id, description, link) FROM stdin;
1	bbbvb	https://lifehacker.ru/special/sport/
22	bbbvb	https://lifehacker.ru/special/sport/
23	bbbvb	https://lifehacker.ru/special/sport/
24	bbbvb	https://lifehacker.ru/special/sport/
42	bbbvb	https://lifehacker.ru/special/sport/
43	bbbvb	https://lifehacker.ru/special/sport/
44	dadawd	https://lifehacker.ru/special/sport/
45	dawdawdawdawd dwadwad	https://lifehacker.ru/special/sport/
46	dwadwadadawdawd dwadawdawdawd	https://lifehacker.ru/special/sport/
62	dwadwadadawdawd dwadawdawdawd	https://lifehacker.ru/special/sport/
63	dwadwadadawdawd dwadawdawdawd	https://lifehacker.ru/special/sport/
122	fdsfsfesfesf	https://lifehacker.ru/special/sport/
123	dwadwafsfs dwadawdwad	https://lifehacker.ru/special/sport/
142	ЗОЖ – норма жизни	https://gazetastep.ru/wp-content/uploads/2022/12/%D0%B7%D0%BE%D0%B6-800x445.jpg
143	Газета - Степь	https://gazetastep.ru/articles/health/%D0%B7%D0%BE%D0%B6-%D0%BD%D0%BE%D1%80%D0%BC%D0%B0-%D0%B6%D0%B8%D0%B7%D0%BD%D0%B8/
\.


--
-- Data for Name: text; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.text (id) FROM stdin;
1
2
22
24
42
45
102
107
\.


--
-- Data for Name: text_part; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.text_part (_order, created_on, id, text_id, value) FROM stdin;
1	2025-05-06 22:35:12.422491+03	1	22	fdsfds fdsfds fds fsdfsdfsdf sdfsdfsdf
2	2025-05-06 22:35:12.494493+03	2	22	dsdsfsfsfe
3	2025-05-06 22:35:12.532181+03	3	22	fdfdgfdgfdhgdfgdwadwad awdawad dawdawd
4	2025-05-06 22:35:12.570096+03	4	22	dsfdsfdsfdsgfdg
5	2025-05-06 22:35:12.615439+03	5	22	dsfdsfdgdfdffd
1	2025-05-06 22:51:02.517258+03	6	24	dsfdvfdvfdcfdfrdfrd dwadw adawd awda waddw
2	2025-05-06 22:51:02.684912+03	7	24	adsfsgdfvfcccursive
3	2025-05-06 22:51:02.708942+03	8	24	fdsf sfdsfd sfds fdsfsdf sffhfgh
4	2025-05-06 22:51:02.719844+03	9	24	sdfsdggtgftbold
5	2025-05-06 22:51:02.733782+03	10	24	vdfvdffhujugfgfgnomrlad d awd awdawd
6	2025-05-06 22:51:02.747432+03	11	24	dgfdvfyhdrrf
1	2025-05-06 22:54:43.374128+03	22	42	dsfdvfdvfdcfdfrdfrd dwadw adawd awda waddw
2	2025-05-06 22:54:43.470244+03	23	42	adsfsgdfvfcccursive
3	2025-05-06 22:54:43.487563+03	24	42	fdsf sfdsfd sfds fdsfsdf sffhfgh
4	2025-05-06 22:54:43.503501+03	25	42	sdfsdggtgftbold
5	2025-05-06 22:54:43.520019+03	26	42	vdfvdffhujugfgfgnomrlad d awd awdawd
6	2025-05-06 22:54:43.539199+03	27	42	dgfdvfyhdrrf
1	2025-05-06 23:00:12.323996+03	28	45	dsfdvfdvfdcfdfrdfrd dwadw adawd awda waddw
2	2025-05-06 23:00:12.404333+03	29	45	adsfsgdfvfcccursive
3	2025-05-06 23:00:12.43723+03	30	45	fdsf sfdsfd sfds fdsfsdf sffhfgh
4	2025-05-06 23:00:12.461051+03	31	45	sdfsdggtgftbold
5	2025-05-06 23:00:12.514544+03	32	45	vdfvdffhujugfgfgnomrlad d awd awdawd
6	2025-05-06 23:00:12.561047+03	33	45	dgfdvfyhdrrf
1	2025-05-07 23:39:55.686908+03	62	102	Здоровый образ жизни начинается с простых, но важных привычек.
1	2025-05-07 23:39:56.679345+03	68	107	Посетите сайт выше для получения более подробной информации
2	2025-05-07 23:39:56.097814+03	63	102	 Питание
3	2025-05-07 23:39:56.162533+03	64	102	, 
4	2025-05-07 23:39:56.189713+03	65	102	 сон 
5	2025-05-07 23:39:56.205038+03	66	102	 и 
6	2025-05-07 23:39:56.222374+03	67	102	 физическая активность 
\.


--
-- Data for Name: view; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.view (article_id, created_on, id, user_id, ip) FROM stdin;
37	2025-05-09 00:31:43.23481+03	1	1	\N
1	2025-05-09 18:34:48.501165+03	2	1	\N
\.


--
-- Name: article_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.article_seq', 41, true);


--
-- Name: article_source_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.article_source_seq', 41, true);


--
-- Name: category_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.category_seq', 6, true);


--
-- Name: comment_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.comment_seq', 181, true);


--
-- Name: fragment_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.fragment_seq', 161, true);


--
-- Name: image_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.image_seq', 161, true);


--
-- Name: mark_article_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.mark_article_seq', 16, true);


--
-- Name: mark_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.mark_seq', 16, true);


--
-- Name: order_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.order_seq', 121, true);


--
-- Name: roll_element_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.roll_element_seq', 61, true);


--
-- Name: source_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.source_seq', 161, true);


--
-- Name: text_part_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.text_part_seq', 81, true);


--
-- Name: view_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.view_seq', 6, true);


--
-- Name: _order _order_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public._order
    ADD CONSTRAINT _order_pkey PRIMARY KEY (id);


--
-- Name: article article_image_id_key; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.article
    ADD CONSTRAINT article_image_id_key UNIQUE (image_id);


--
-- Name: article_link article_link_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.article_link
    ADD CONSTRAINT article_link_pkey PRIMARY KEY (id);


--
-- Name: article article_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.article
    ADD CONSTRAINT article_pkey PRIMARY KEY (id);


--
-- Name: article_source article_source_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.article_source
    ADD CONSTRAINT article_source_pkey PRIMARY KEY (id);


--
-- Name: article_source article_source_source_id_key; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.article_source
    ADD CONSTRAINT article_source_source_id_key UNIQUE (source_id);


--
-- Name: bold_part bold_part_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.bold_part
    ADD CONSTRAINT bold_part_pkey PRIMARY KEY (id);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: category category_title_key; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_title_key UNIQUE (title);


--
-- Name: comment comment_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (id);


--
-- Name: cursive_part cursive_part_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.cursive_part
    ADD CONSTRAINT cursive_part_pkey PRIMARY KEY (id);


--
-- Name: fragment_image fragment_image_image_id_key; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.fragment_image
    ADD CONSTRAINT fragment_image_image_id_key UNIQUE (image_id);


--
-- Name: fragment_image fragment_image_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.fragment_image
    ADD CONSTRAINT fragment_image_pkey PRIMARY KEY (id);


--
-- Name: fragment fragment_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.fragment
    ADD CONSTRAINT fragment_pkey PRIMARY KEY (id);


--
-- Name: image image_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT image_pkey PRIMARY KEY (id);


--
-- Name: image image_source_id_key; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT image_source_id_key UNIQUE (source_id);


--
-- Name: link_part link_part_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.link_part
    ADD CONSTRAINT link_part_pkey PRIMARY KEY (id);


--
-- Name: mark_article mark_article_mark_id_key; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.mark_article
    ADD CONSTRAINT mark_article_mark_id_key UNIQUE (mark_id);


--
-- Name: mark_article mark_article_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.mark_article
    ADD CONSTRAINT mark_article_pkey PRIMARY KEY (id);


--
-- Name: mark mark_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.mark
    ADD CONSTRAINT mark_pkey PRIMARY KEY (id);


--
-- Name: mark mark_value_key; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.mark
    ADD CONSTRAINT mark_value_key UNIQUE (value);


--
-- Name: quote quote_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.quote
    ADD CONSTRAINT quote_pkey PRIMARY KEY (id);


--
-- Name: roll_element roll_element_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.roll_element
    ADD CONSTRAINT roll_element_pkey PRIMARY KEY (id);


--
-- Name: roll roll_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.roll
    ADD CONSTRAINT roll_pkey PRIMARY KEY (id);


--
-- Name: source source_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.source
    ADD CONSTRAINT source_pkey PRIMARY KEY (id);


--
-- Name: text_part text_part_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.text_part
    ADD CONSTRAINT text_part_pkey PRIMARY KEY (id);


--
-- Name: text text_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.text
    ADD CONSTRAINT text_pkey PRIMARY KEY (id);


--
-- Name: view view_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.view
    ADD CONSTRAINT view_pkey PRIMARY KEY (id);


--
-- Name: article_category_id_idx; Type: INDEX; Schema: public; Owner: daniel
--

CREATE INDEX article_category_id_idx ON public.article USING btree (category_id);


--
-- Name: article_title_idx; Type: INDEX; Schema: public; Owner: daniel
--

CREATE INDEX article_title_idx ON public.article USING btree (title);


--
-- Name: category_parent_category_id_idx; Type: INDEX; Schema: public; Owner: daniel
--

CREATE INDEX category_parent_category_id_idx ON public.category USING btree (parent_category_id);


--
-- Name: comment_article_id_idx; Type: INDEX; Schema: public; Owner: daniel
--

CREATE INDEX comment_article_id_idx ON public.comment USING btree (article_id);


--
-- Name: fragment_article_id_order_idx; Type: INDEX; Schema: public; Owner: daniel
--

CREATE INDEX fragment_article_id_order_idx ON public.fragment USING btree (article_id, _order);


--
-- Name: mark_article_article_id_idx; Type: INDEX; Schema: public; Owner: daniel
--

CREATE INDEX mark_article_article_id_idx ON public.mark_article USING btree (article_id);


--
-- Name: mark_article_user_id_idx; Type: INDEX; Schema: public; Owner: daniel
--

CREATE INDEX mark_article_user_id_idx ON public.mark_article USING btree (user_id);


--
-- Name: order_fragment_id_idx; Type: INDEX; Schema: public; Owner: daniel
--

CREATE INDEX order_fragment_id_idx ON public._order USING btree (fragment_id);


--
-- Name: roll_element_roll_id_idx; Type: INDEX; Schema: public; Owner: daniel
--

CREATE INDEX roll_element_roll_id_idx ON public.roll_element USING btree (roll_id);


--
-- Name: view_created_on_idx; Type: INDEX; Schema: public; Owner: daniel
--

CREATE INDEX view_created_on_idx ON public.view USING btree (created_on);


--
-- Name: fragment_image fk16tm1bh4vs9aejjairjo3jlb9; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.fragment_image
    ADD CONSTRAINT fk16tm1bh4vs9aejjairjo3jlb9 FOREIGN KEY (image_id) REFERENCES public.image(id);


--
-- Name: fragment_image fk330sjct2bt39ppeoudjuw7ify; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.fragment_image
    ADD CONSTRAINT fk330sjct2bt39ppeoudjuw7ify FOREIGN KEY (id) REFERENCES public._order(id);


--
-- Name: _order fk5ej30bq1wn1vv0c4pyxhkhfqr; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public._order
    ADD CONSTRAINT fk5ej30bq1wn1vv0c4pyxhkhfqr FOREIGN KEY (fragment_id) REFERENCES public.fragment(id);


--
-- Name: bold_part fk5rff4yet96i45aulcnim29jng; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.bold_part
    ADD CONSTRAINT fk5rff4yet96i45aulcnim29jng FOREIGN KEY (id) REFERENCES public.text_part(id);


--
-- Name: comment fk5yx0uphgjc6ik6hb82kkw501y; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fk5yx0uphgjc6ik6hb82kkw501y FOREIGN KEY (article_id) REFERENCES public.article(id);


--
-- Name: fragment fk62dqqxhifwb8il7siykwovkyg; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.fragment
    ADD CONSTRAINT fk62dqqxhifwb8il7siykwovkyg FOREIGN KEY (article_id) REFERENCES public.article(id);


--
-- Name: article_link fk62ksvswlpxrf3h77xmk11q96y; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.article_link
    ADD CONSTRAINT fk62ksvswlpxrf3h77xmk11q96y FOREIGN KEY (id) REFERENCES public._order(id);


--
-- Name: image fk9nusrsbt6b7fmo7en9np2e60i; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT fk9nusrsbt6b7fmo7en9np2e60i FOREIGN KEY (source_id) REFERENCES public.source(id);


--
-- Name: article fka8st57l43fmam691umn5bw37u; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.article
    ADD CONSTRAINT fka8st57l43fmam691umn5bw37u FOREIGN KEY (image_id) REFERENCES public.image(id);


--
-- Name: text fkbhx8xsbaid2x40ibfoeyx78um; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.text
    ADD CONSTRAINT fkbhx8xsbaid2x40ibfoeyx78um FOREIGN KEY (id) REFERENCES public._order(id);


--
-- Name: roll_element fkbprrq4g2st3lq1n7b3wxrvd8w; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.roll_element
    ADD CONSTRAINT fkbprrq4g2st3lq1n7b3wxrvd8w FOREIGN KEY (roll_id) REFERENCES public.roll(id);


--
-- Name: article_source fkbvx4sq8ugbwo65rc4rmgus7lr; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.article_source
    ADD CONSTRAINT fkbvx4sq8ugbwo65rc4rmgus7lr FOREIGN KEY (article_id) REFERENCES public.article(id);


--
-- Name: cursive_part fkc7wqvclfemhxqy60i1b9ufp94; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.cursive_part
    ADD CONSTRAINT fkc7wqvclfemhxqy60i1b9ufp94 FOREIGN KEY (id) REFERENCES public.text_part(id);


--
-- Name: text_part fkcghubsd6j4v264bl2x5uu1ivx; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.text_part
    ADD CONSTRAINT fkcghubsd6j4v264bl2x5uu1ivx FOREIGN KEY (text_id) REFERENCES public.text(id);


--
-- Name: quote fkfgkt1q6jxhl0i43srwioqvpvr; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.quote
    ADD CONSTRAINT fkfgkt1q6jxhl0i43srwioqvpvr FOREIGN KEY (id) REFERENCES public._order(id);


--
-- Name: mark_article fkh5shgetfy858kbpe65qfhnoq5; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.mark_article
    ADD CONSTRAINT fkh5shgetfy858kbpe65qfhnoq5 FOREIGN KEY (article_id) REFERENCES public.article(id);


--
-- Name: roll fkhnxw7a6kbf8uvc0jqq7q6w54b; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.roll
    ADD CONSTRAINT fkhnxw7a6kbf8uvc0jqq7q6w54b FOREIGN KEY (id) REFERENCES public._order(id);


--
-- Name: view fki2335bbq2c3fxbtdnsq849jgs; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.view
    ADD CONSTRAINT fki2335bbq2c3fxbtdnsq849jgs FOREIGN KEY (article_id) REFERENCES public.article(id);


--
-- Name: link_part fkjg43y6t84eamqwaseettfdqcc; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.link_part
    ADD CONSTRAINT fkjg43y6t84eamqwaseettfdqcc FOREIGN KEY (id) REFERENCES public.text_part(id);


--
-- Name: comment fkmk3c8pbvysjndxywunibl2voc; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fkmk3c8pbvysjndxywunibl2voc FOREIGN KEY (comment_id) REFERENCES public.comment(id);


--
-- Name: article_source fkmum5jlrwiojwvt6av7fbxa7qd; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.article_source
    ADD CONSTRAINT fkmum5jlrwiojwvt6av7fbxa7qd FOREIGN KEY (source_id) REFERENCES public.source(id);


--
-- Name: mark_article fkn3j8us09a3whg51pxkdtdag6b; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.mark_article
    ADD CONSTRAINT fkn3j8us09a3whg51pxkdtdag6b FOREIGN KEY (mark_id) REFERENCES public.mark(id);


--
-- Name: category fks2ride9gvilxy2tcuv7witnxc; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT fks2ride9gvilxy2tcuv7witnxc FOREIGN KEY (parent_category_id) REFERENCES public.category(id);


--
-- Name: article_link fkslmybsncakisj8birsdy76k2s; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.article_link
    ADD CONSTRAINT fkslmybsncakisj8birsdy76k2s FOREIGN KEY (article_id) REFERENCES public.article(id);


--
-- Name: article fky5kkohbk00g0w88fi05k2hcw; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.article
    ADD CONSTRAINT fky5kkohbk00g0w88fi05k2hcw FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- PostgreSQL database dump complete
--

