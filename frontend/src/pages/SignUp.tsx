import {useState} from 'react';
import {signup} from '../services/api';
import {useNavigate} from 'react-router-dom';
import '../App.css';

const Register = () => {
    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [repeatedPassword, setRepeatedPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSignup = async () => {
        if (password !== repeatedPassword) {
            setError("Passwords do not match")
            return;
        }
        if (!userName.trim() || !password.trim()) {
            setError("Username and password cannot be empty")
            return;
        }
        setError('');
        try {
            const res = await signup(userName.trim(), password.trim());
            localStorage.setItem('userId', res);
            navigate('/notes');
        } catch (err) {
            setError(err.message || "Signup Failed")
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
            {error && <p className="error-message">{error}</p>}
        </div>
    );
};

export default Register;
