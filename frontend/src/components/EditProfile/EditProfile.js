import "../../config";
import axios from "axios";
import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";

import "./EditProfile.scss";

export default function EditProfile() {
    const maxChar = 150;
    const [loading, setLoading] = useState(true);

    const [displayName, setDisplayName] = useState("");
    const [biography, setBiography] = useState("");
    const [email, setEmail] = useState("");
    const [birthday, setBirthday] = useState(new Date());
    const [editStatus, setEditStatus] = useState("profile");

    const [originalBiography, setOriginalBiography] = useState("");
    const [originalEmail, setOriginalEmail] = useState("");
    const [originalBirthday, setOriginalBirthday] = useState();
    const [originalDisplayName, setOriginalDisplayName] = useState("");

    const [editBioSuccess, setEditBioSuccess] = useState(false);
    const [editEmailSuccess, setEditEmailSuccess] = useState(false);
    const [editDisplayNameSuccess, setEditDisplayNameSuccess] = useState(false);
    const [editPasswordSuccess, setEditPasswordSuccess] = useState(false);
    const [editBirthdaySuccess, setEditBirthdaySuccess] = useState(false);

    const [charLeft, setCharLeft] = useState(maxChar);
    const history = useHistory();

    useEffect(() => {
        const fetchCurrentUser = () => {
            axios.get(global.config.BACKEND_URL + "/account")
                .then(
                    (response) => {
                        setDisplayName(response.data.displayName);
                        setBiography(response.data.biography);
                        setEmail(response.data.email);
                        setBirthday(response.data.dateOfBirth);
                        setOriginalBirthday(response.data.dateOfBirth);
                        setOriginalEmail(response.data.email);
                        setOriginalBiography(response.data.biography);
                        setOriginalDisplayName(response.data.displayName);
                        setCharLeft(() => {
                            return response.data.biography ?
                                maxChar - response.data.biography.length : maxChar
                        });
                        setLoading(false);
                    })
                .catch(() => {
                    history.push("/register");
                })
        }
        fetchCurrentUser();
    }, [loading, history])

    useEffect(() => {
        //This useEffect purpose is to force the page to re-render without having to make an API request
    }, [displayName, biography, email, editStatus, editBioSuccess, editDisplayNameSuccess, editEmailSuccess])

    const handleTyping = (e, setterFunction) => {
        setterFunction(e.target.value);
    }

    const handleEditStatus = (e, status) => {
        e.preventDefault();
        setEditStatus(status);
    }

    const handleCharacterCountBiography = (e) => {
        e.preventDefault();
        const input = e.target.value;

        if (input.length > maxChar) {
            return;
        }

        setCharLeft(maxChar - input.length);
        setBiography(input);
    }

    const updateProfile = (event) => {
        event.preventDefault();
        if (event.target.biography.value !== originalBiography) {
            axios.put(global.config.BACKEND_URL + `/account/profile/biographyUpdate`,
                {
                    "biography": event.target.biography.value
                }).then(() => {
                    setEditBioSuccess(true);
                }).catch((error) => {
                    setEditBioSuccess(false);
                    console.log(error.response.data);
                });
        }

        if (event.target.email.value !== originalEmail) {
            axios.put(global.config.BACKEND_URL + `/account/profile/emailUpdate`,
                {
                    "email": event.target.email.value
                }).then(() => {
                    setEditEmailSuccess(true);
                }).catch((error) => {
                    setEditEmailSuccess(false);
                    alert(error.response.data.message);
                })
        }

        if (event.target.displayName.value !== originalDisplayName) {
            axios.put(global.config.BACKEND_URL + `/account/profile/displayNameUpdate`,
                {
                    "displayName": event.target.displayName.value
                }).then(() => {
                    setEditDisplayNameSuccess(true);
                }).catch((error) => {
                    setEditDisplayNameSuccess(false);
                    alert(error.response.data.message);
                });
        }
    }

    const updateAccount = (event) => {
        event.preventDefault();
        if (!(event.target.password.value.length <= 0)) {
            axios.put(global.config.BACKEND_URL + `/account/passwordModification`,
                {
                    "password": event.target.password.value
                }).then(() => {
                    setEditPasswordSuccess(true);
                }).catch((error) => {
                    setEditPasswordSuccess(false);
                    alert(error.response.data.message);
                });
        }

        if (event.target.birthday.value !== originalBirthday) {
            axios.put(global.config.BACKEND_URL + `/account/birthdayModification`,
                {
                    "birthday": event.target.birthday.value
                }).then(() => {
                    setEditBirthdaySuccess(true);
                }).catch((error) => {
                    setEditBirthdaySuccess(false);
                    alert(error.response.data.message);
                });
        }
    }

    return (
        <div className="edit-profile">
            {loading ? "loading"
                : <div className="edit-profile-page">
                    <div className="sidebar">
                        <nav class="nav">
                            <ul>
                                <li class="active">Instagram++</li>
                                <li><a href="#!" onClick={(e) => handleEditStatus(e, "profile")}>Edit Profile</a></li>
                                <li><a href="#!" onClick={(e) => handleEditStatus(e, "account")} >Edit Account</a></li>
                                <li><a href="#!" style={{ borderRight: "none" }}>Contact Support</a></li>
                            </ul>
                        </nav>
                    </div>
                    {(editStatus === "profile")
                        ? <div className="edit-profile">
                            <form className="edit-profile-form" onSubmit={updateProfile}>
                                <h1>Edit Profile</h1>
                                <div>
                                    <label>Display Name</label><br />
                                    <input type="text" name="displayName" value={displayName} onChange={(e) => handleTyping(e, setDisplayName)}></input>
                                    <div className="success-wrapper">
                                        {editDisplayNameSuccess ? <div className="success">Success</div> : ''}
                                    </div>
                                </div><br />
                                <div>
                                    <label>Bio</label><br />
                                    <textarea name="biography" className="edit-profile-textarea" value={biography} onChange={(e) => handleCharacterCountBiography(e)}></textarea>
                                    <div className="success-wrapper">
                                        {editBioSuccess ? <div className="success">Success</div> : ''}
                                        <div className="char-count">Character left: {charLeft}</div>
                                    </div>
                                </div><br />
                                <div>
                                    <label>Email</label><br />
                                    <input type="text" name="email" value={email} onChange={(e) => handleTyping(e, setEmail)}></input>
                                    {editEmailSuccess ? <div className="success">Success</div> : ''}
                                </div>
                                <div className="edit-profile-button">
                                    <button className="button">Submit</button>
                                </div><br />
                            </form>
                        </div>
                        : <div className="edit-profile">
                            <form className="edit-profile-form" onSubmit={updateAccount} >
                                <h1>Edit Account</h1>
                                <div>
                                    <label style={{ verticalAlign: "middle" }}>Password Modification</label><br />
                                    <div>
                                        <input type="password" name="password" placeholder="Enter a new password"></input>
                                    </div>
                                    <div>
                                        {editPasswordSuccess ? <div className="success">Success</div> : ''}
                                    </div>
                                </div><br />
                                <div>
                                    <label>Birthday</label><br />
                                    <input type="date" name="birthday" value={birthday} onChange={(e) => handleTyping(e, setBirthday)}></input>
                                    <div className="success-wrapper">
                                        {editBirthdaySuccess ? <div className="success">Success</div> : ''}
                                    </div>
                                </div>
                                <div className="edit-profile-button">
                                    <button className="button">Submit</button>
                                </div>
                            </form>
                        </div>
                    }
                </div>
            }
        </div>
    )
}