<?php
  $method = 'node.get';
  $api_key = 'f023ef405968a70aa969cea588ab237c'; // Your API key.
  $sessid = '8a3691a571f3530cb3d802691ea9045a'; // Your session ID.
  $domain = 'mobile.phillymtb.org'; //$_SERVER['HTTP_HOST'];  
  $timestamp = (string) time();
  $nonce = rand(1225, 12315);
  $hash = hash_hmac('sha256', $timestamp .';'.$domain .';'. $nonce .';'. $method, $api_key);
  
  $ch = curl_init();
  curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($ch, CURLOPT_POST, 1);
  curl_setopt($ch, CURLOPT_URL, 'http://www.phillymtb.org:8080/services/json');

  //prepare the field values being posted to the JSON service (WITH key authentication)
  $data = array(
    'method' => '"'. $method .'"',
    'hash' => '"'. $hash .'"',
    'domain_name' => '"'. $domain .'"',
    'domain_time_stamp' => '"'. $timestamp .'"',
    'nonce' => '"'. $nonce .'"',
    'sessid' => '"'. $sessid .'"', // If you're using sessid, uncomment this line
    'nid' => '"114"',
    );
  curl_setopt($ch, CURLOPT_POSTFIELDS, $data);

  //make the request
  $result = curl_exec($ch);

print_r($result);
?>

