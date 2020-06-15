function [nP, nQ] = matrix_factorization(R, P, Q, K, steps, alpha, beta)
for x = 1:steps
    Sum = P * Q;
    Error = R - Sum;
    [r,c] = find(R == 0);
    for ii = 1:size(r)
        Error(r(ii),c(ii))=0;
    end
    for i = 1:size(Error,1)
        for k = 1:K
            for j = 1:size(Error,2)
                P(i,k) = P(i,k) + alpha * (2 * Error(i,j) * Q(k,j) - beta * P(i,k));
            end
        end
    end
    for j = 1:size(Error,2)
        for k = 1:K
            for i = 1:size(Error,1)
                Q(k,j) = Q(k,j) + alpha * (2 * Error(i,j) * P(i,k) - beta * Q(k,j));
            end
        end
    end
end    
nP = P;
nQ = Q;


