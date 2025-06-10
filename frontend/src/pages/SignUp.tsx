import { useState } from 'react';
import { signup } from '../services/api';
import { useNavigate } from 'react-router-dom';
import '../App.css';

const Register = () => {
    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [repeatedPassword, setRepeatedPassword] = useState('');
    const navigate = useNavigate();

    const handleSignup = async () => {
        if(password !== repeatedPassword){
            alert("password do not match");
        }
        try {
            const res = await signup(userName, password);
            localStorage.setItem('userId', res);
            navigate('/notes');
        } catch {
            alert('Signup failed');
        }
    };

    return (
        <div className='container'>
            <h2>Sign Up</h2>
            <input
                type="text"
                placeholder="Username"
                value={userName}
                onChange={(e) => setUserName(e.target.value)}
                className="input"
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="input"
            />
            <input
                type="password"
                placeholder="Repeat Password"
                value={repeatedPassword}
                onChange={(e) => setRepeatedPassword(e.target.value)}
                className="input"
            />
            <button onClick={handleSignup} className="button">
                Sign Up
            </button>
        </div>
    );
};

export default Register;
