# Registrering av lag #

## request ##
> $ curl -XPOST http://localhost:9090/api/team/add/0xDEADBEEF

## response ##
{
  "X-TEAM-KEY": "N0D60H9fA7k93YLBXX0z/oKxnEGYle9eKa2qJP8Bh8A="
}

# Uthenting av flag #

## request ## 

> $ curl -XGET -H "X-TEAM-KEY: N0D60H9fA7k93YLBXX0z/oKxnEGYle9eKa2qJP8Bh8A=" http://localhost:9090/api/flag/list

## response ##

[
  {
    "flagName": "eps1.1_ones-and-zer0es.mpeg",
    "flagId": "ccedab9a-beba-405e-be77-f88fe9cb2007"
  },
  {
    "flagName": "eps1.5_br4ve-trave1er.asf",
    "flagId": "e5a71f57-1a7e-4c94-b518-2864fcffaf88"
  },
  {
    "flagName": "eps1.3_da3m0ns.mp4",
    "flagId": "9625e1c7-55a1-48f3-b14f-98a493f99024"
  },
  {
    "flagName": "eps1.6_v1ew-s0urce.flv",
    "flagId": "ffb4885a-3b3c-4c8f-9d50-baa40abf0558"
  },
  {
    "flagName": "eps1.7_wh1ter0se.m4v",
    "flagId": "bca73a7f-3372-4dc9-a2e9-e688e74b192a"
  },
  {
    "flagName": "eps1.9_zer0-day.avi",
    "flagId": "997c5ed5-1292-4c23-bbb6-37cfdaa5b019"
  },
  {
    "flagName": "eps1.0_hellofriend.mov",
    "flagId": "f47dd28c-6403-4fc4-a0f7-dd94b843509d"
  },
  {
    "flagName": "eps1.2_d3bug.mkv",
    "flagId": "0f89f0f8-54c0-4cd9-b9cb-b14f3b1284cf"
  },
  {
    "flagName": "eps1.4_3xpl0its.wmv",
    "flagId": "7caa3189-64af-4b49-a774-4b1bbe125487"
  },
  {
    "flagName": "eps1.8_m1rr0r1ng.qt",
    "flagId": "89168289-9faf-46e5-bb3d-e63e351b5e28"
  }
]

# svar på flag #

## request ##

> $ curl -XPOST -H "X-TEAM-KEY: N0D60H9fA7k93YLBXX0z/oKxnEGYle9eKa2qJP8Bh8A=" -H "Content-type: application/json" -d "{ "flagId": "ccedab9a-beba-405e-be77-f88fe9cb2007", "flag": "Sam Esmail" }" http://localhost:9090/api/flag/

# vis poengsum og lagoversikt #

## request ##

> $ curl -XGET http://localhost:9090/api/public/board

## response ##
{
  "score": {
    "0xDEADBEEF": 1
  },
  "title": "Sopra Steria CtF 2017"
}
