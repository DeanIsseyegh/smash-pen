import React from 'react';
import '../pure-release-1.0.0/forms-min.css';

interface LogInErrorProps {
    isLogInErr: boolean;
}

const LogInError = ({isLogInErr}: LogInErrorProps) =>
    <div>{isLogInErr &&  <p>There was a problem logging in. Please check your username and password.</p>}</div>;


export default LogInError
