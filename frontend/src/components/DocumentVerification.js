import React, { useEffect, useState } from 'react';

const DocumentVerification = ({ match }) => {
  const [referenceId, setReferenceId] = useState(null);

  useEffect(() => {
    // Fetch the reference ID from the backend (assuming response includes it)
    const fetchData = async () => {
      const response = await fetch(`http://localhost:8080/salon/verify-docs/${match.params.referenceId}`);
      const data = await response.json();
      setReferenceId(data.referenceId); // Assuming the response has referenceId
    };

    fetchData();
  }, [match.params.referenceId]);

  return (
    <div className="document-verification-page">
      <h1>Document Verification In Progress</h1>
      {referenceId ? (
        <div>
          <p>We are verifying your documents, and your salon will be live in 2-3 business days.</p>
          <p>Your reference ID is: <strong>{referenceId}</strong></p>
          <p>If you have any questions, please contact our customer care at +91 9414333018.</p>
        </div>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default DocumentVerification;
